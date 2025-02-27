package com.zaurh.movieappintern2.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zaurh.movieappintern2.data.repository.AuthRepositoryImpl
import com.zaurh.movieappintern2.data.repository.DatabaseRepositoryImpl
import com.zaurh.movieappintern2.data.repository.MovieRepoImpl
import com.zaurh.movieappintern2.data.repository.ReviewRepositoryImpl
import com.zaurh.movieappintern2.domain.repository.AuthRepository
import com.zaurh.movieappintern2.domain.repository.DatabaseRepository
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.domain.repository.ReviewRepository
import com.zaurh.movieappintern2.preferences.DataStoreSettings
import com.zaurh.movieappintern2.service.MovieApi
import com.zaurh.movieappintern2.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository {
        return MovieRepoImpl(movieApi)
    }

    @Singleton
    @Provides
    fun provideReviewRepository(firestore: FirebaseFirestore): ReviewRepository {
        return ReviewRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository =
        AuthRepositoryImpl(
            auth = auth,
            firestore = firestore
        )

    @Singleton
    @Provides
    fun provideFirestoreRepository(firestore: FirebaseFirestore): DatabaseRepository = DatabaseRepositoryImpl(
        firestore = firestore
    )

    @Provides
    @Singleton
    fun provideDataStoreSettings(@ApplicationContext context: Context): DataStoreSettings {
        return DataStoreSettings(context)
    }


}