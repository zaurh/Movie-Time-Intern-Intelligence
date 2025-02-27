package com.zaurh.movieappintern2.domain.repository

import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    fun getMyReviews(userId: String): Flow<Resource<List<com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData>>>
    fun getReviews(movieId: Int): Flow<Resource<List<com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData>>>
    fun addReview(reviewData: com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData): Flow<Resource<Boolean>>
}