package com.zaurh.movieappintern2.data.models.firebase

data class UserData(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val favoriteMovies: List<com.zaurh.movieappintern2.data.models.firebase.FavoriteData> = listOf()
)
