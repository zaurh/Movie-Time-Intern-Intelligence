package com.zaurh.movieappintern2.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.zaurh.movieappintern2.domain.repository.AuthRepository
import com.zaurh.movieappintern2.domain.repository.DatabaseRepository
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


data class UserDataState(
    val loading: Boolean = false,
    val data: com.zaurh.movieappintern2.data.models.firebase.UserData? = null,
    val error: String? = null
)

data class SignInState(
    val loading: Boolean = false,
    val error: String? = null
)

data class SignUpState(
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn = _isSignedIn.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState = _userState.asStateFlow()

    private val _userData = MutableStateFlow(UserDataState())
    val userData = _userData.asStateFlow()

    init {
        getCurrentUser()
    }

    fun signUp(email: String, password: String, confirmPassword: String, onSuccess: () -> Unit) {
        _signUpState.update { it.copy(loading = true) }
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _signUpState.update { it.copy(loading = false, error = "Please fill all fields.") }
            return
        }
        if (password != confirmPassword) {
            _signUpState.update { it.copy(loading = false, error = "Passwords do not match.") }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(email, password).collect { result ->
                Log.d("auth", "${result.message}")
                when (result) {
                    is Resource.Error -> {
                        _signUpState.update { it.copy(loading = false, error = result.message) }
                    }

                    is Resource.Loading -> {
                        _signUpState.update { it.copy(loading = true) }
                    }

                    is Resource.Success -> {
                        _signUpState.update { it.copy(loading = false, error = "") }
                        _userState.value = result.data
                        _isSignedIn.value = true
                        getUserData()
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                }
            }
        }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        _signInState.update { it.copy(loading = true) }
        if (email.isBlank() || password.isBlank()) {
            _signInState.update { it.copy(loading = false, error = "Please fill email and password.") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(email, password).collect { result ->
                Log.d("auth_Sign_in", "${result.message}")
                when (result) {
                    is Resource.Error -> {
                        _signInState.update { it.copy(loading = false, error = result.message) }
                    }

                    is Resource.Loading -> {
                        _signInState.update { it.copy(loading = true) }
                    }

                    is Resource.Success -> {
                        _signInState.update { it.copy(loading = false, error = "") }

                        _userState.value = result.data
                        _isSignedIn.value = true
                        getUserData()
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                }
            }
        }
    }

    fun updateUsername(username: String) {
        val updatedUser = userData.value.data?.copy(username = username)
        _userData.update { it.copy(data = updatedUser) }
    }

    fun addToFavorite(favoriteData: com.zaurh.movieappintern2.data.models.firebase.FavoriteData){
        val updatedFavoriteMovies = userData.value.data?.favoriteMovies?.plus(favoriteData)
        updatedFavoriteMovies?.let { updatedMovies ->
            val updatedUser = userData.value.data?.copy(favoriteMovies = updatedMovies)
            _userData.update { it.copy(data = updatedUser) }
        }
    }

    fun removeFromFavorite(favoriteData: com.zaurh.movieappintern2.data.models.firebase.FavoriteData){
        val updatedFavoriteMovies = userData.value.data?.favoriteMovies?.minus(favoriteData)
        updatedFavoriteMovies?.let { updatedMovies ->
            val updatedUser = userData.value.data?.copy(favoriteMovies = updatedMovies)
            _userData.update { it.copy(data = updatedUser) }
        }
    }

    private fun getCurrentUser() {
        _userState.value = authRepository.getCurrentUser()
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            userState.value?.uid?.let { uid ->
                databaseRepository.getUserData(uid).collect { result ->
                    Log.d("dsjadlsaads", "${result.message}")
                    when (result) {
                        is Resource.Error -> {

                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _userData.update { it.copy(data = result.data) }
                        }
                    }
                }
                _isSignedIn.value = true
            }
        }
    }

    fun clearSignInState() {
        _signInState.update { it.copy(error = null) }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signOut()
            _isSignedIn.value = false
            _userState.value = null
            _userData.value = UserDataState()
        }
    }
}