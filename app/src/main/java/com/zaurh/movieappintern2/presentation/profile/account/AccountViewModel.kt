package com.zaurh.movieappintern2.presentation.profile.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.movieappintern2.domain.repository.DatabaseRepository
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UsernameState(
    val username: String = "",
    val loading: Boolean = false,
    val message: String = ""
)
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _usernameState = MutableStateFlow(UsernameState())
        val usernameState = _usernameState.asStateFlow()

    fun setUsername(username: String) {
        _usernameState.value = _usernameState.value.copy(username = username)
    }

    fun updateUsername(userId: String, onSuccess: () -> Unit) {
        _usernameState.update { it.copy(loading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val username = usernameState.value.username
            databaseRepository.updateUsername(userId, username).collect { result ->
                when(result) {
                    is Resource.Error -> {
                        _usernameState.update { it.copy(loading = false, message = result.message ?: "Unknown error") }
                    }
                    is Resource.Loading -> {
                        _usernameState.update { it.copy(loading = true, message = "") }
                    }
                    is Resource.Success -> {
                        onSuccess()
                        _usernameState.update { it.copy(username = username, loading = false, message = "Successfully updated.") }
                    }
                }
            }
        }
    }
}