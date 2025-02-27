package com.zaurh.movieappintern2.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData
import com.zaurh.movieappintern2.domain.repository.ReviewRepository
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore
) : ReviewRepository {

    private val collection = firestore.collection("reviews")

    override fun getMyReviews(userId: String): Flow<Resource<List<com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData>>> = flow {
        emit(Resource.Loading())
        try {
            val snapshot = collection.whereEqualTo("author.id", userId).get().await()
            val reviews = snapshot.toObjects(com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData::class.java)
            emit(Resource.Success(reviews))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun getReviews(movieId: Int): Flow<Resource<List<com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData>>> = flow {
        emit(Resource.Loading())
        try {
            val snapshot = collection.whereEqualTo("movie.id", movieId).get().await()
            val reviews = snapshot.toObjects(com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData::class.java)
            emit(Resource.Success(reviews))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override fun addReview(reviewData: com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            collection.add(reviewData).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}