package com.zaurh.movieappintern2.data.models.firebase.reviews

import com.google.firebase.Timestamp
import com.zaurh.movieappintern2.domain.models.MovieDetails

data class ReviewData(
    val id: String = "",
    val author: com.zaurh.movieappintern2.data.models.firebase.UserData? = null,
    val opinion: String = "",
    val rate: com.zaurh.movieappintern2.data.models.firebase.reviews.Rate = com.zaurh.movieappintern2.data.models.firebase.reviews.Rate.NONE,
    val timestamp: Timestamp = Timestamp.now(),
    val movie: MovieDetails? = null,
)

