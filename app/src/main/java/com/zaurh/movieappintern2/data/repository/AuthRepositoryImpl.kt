package com.zaurh.movieappintern2.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.zaurh.movieappintern2.data.models.firebase.UserData
import com.zaurh.movieappintern2.domain.repository.AuthRepository
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("User is null")

            val user = UserData(
                id = firebaseUser.uid,
                username = "no_name_user",
                email = email,
                password = password,
                favoriteMovies = emptyList()
            )

            firestore.collection("users").document(firebaseUser.uid).set(user).await()
            emit(Resource.Success(firebaseUser))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result.user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }


    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun signOut() {
        auth.signOut()
    }




}