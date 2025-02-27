package com.zaurh.movieappintern2.domain.repository

import com.zaurh.movieappintern2.data.models.firebase.FavoriteData
import com.zaurh.movieappintern2.data.models.firebase.UserData
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getUserData(uid: String): Flow<Resource<UserData>>
    fun updateUsername(userId: String, username: String): Flow<Resource<UserData>>
    fun addToFavorites(userId: String, movie: FavoriteData): Flow<Resource<FavoriteData>>
    fun removeFromFavorites(userId: String, movie: FavoriteData): Flow<Resource<FavoriteData>>
}