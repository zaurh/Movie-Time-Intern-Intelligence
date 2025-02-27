package com.zaurh.movieappintern2.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.zaurh.movieappintern2.data.models.firebase.FavoriteData
import com.zaurh.movieappintern2.data.models.firebase.UserData
import com.zaurh.movieappintern2.domain.repository.DatabaseRepository
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): DatabaseRepository {

    override fun getUserData(uid: String): Flow<Resource<UserData>> = flow {
        emit(Resource.Loading())
        try {
            val documentSnapshot = firestore.collection("users").document(uid).get().await()
            if (documentSnapshot.exists()) {
                val userData = documentSnapshot.toObject(UserData::class.java)
                if (userData != null) {
                    emit(Resource.Success(userData))
                }else{
                    emit(Resource.Error("User data is null"))
                }
            } else {
                emit(Resource.Error("User data not found"))
            }

        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun updateUsername(userId: String, username: String): Flow<Resource<UserData>> = flow {
        emit(Resource.Loading())
        try {
            if (username.isEmpty()){
                emit(Resource.Error("Username cannot be empty"))
                return@flow
            }
            val currentUser = firestore.collection("users").document(userId)
            currentUser.update("username", username).await()
            val updatedUser = currentUser.get().await().toObject(UserData::class.java)
            if (updatedUser != null) {
                emit(Resource.Success(updatedUser))
                updateReviewsUsername(userId, username)
            }
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun addToFavorites(userId: String, movie: FavoriteData): Flow<Resource<FavoriteData>> = flow{
        emit(Resource.Loading())
        try {

            val currentUser = firestore.collection("users").document(userId)
            currentUser.update("favoriteMovies", FieldValue.arrayUnion(movie)).await()
            emit(Resource.Success(movie))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun removeFromFavorites(userId: String, movie: FavoriteData): Flow<Resource<FavoriteData>> = flow {
        emit(Resource.Loading())
        try {
            val currentUser = firestore.collection("users").document(userId)
            currentUser.update("favoriteMovies", FieldValue.arrayRemove(movie)).await()
            emit(Resource.Success(movie))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }


    private suspend fun updateReviewsUsername(userId: String, newUsername: String) {
        val reviewsCollection = firestore.collection("reviews")

        try {
            val reviewsSnapshot = reviewsCollection.whereEqualTo("author.id", userId).get().await()
            val batch = firestore.batch()

            for (document in reviewsSnapshot.documents) {
                batch.update(document.reference, "author.username", newUsername)
            }

            batch.commit().await()

        } catch (e: Exception) {
            Log.e("Firestore", "Error updating reviews: ${e.localizedMessage}")
        }
    }

}