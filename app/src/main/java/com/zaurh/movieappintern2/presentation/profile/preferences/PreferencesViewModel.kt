package com.zaurh.movieappintern2.presentation.profile.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaurh.movieappintern2.preferences.DataStoreSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val dataStoreSettings: DataStoreSettings
): ViewModel() {

    private val _switchState = MutableStateFlow(false)
        val switchState = _switchState.asStateFlow()

    init {
        getDarkMode()
    }

    private fun getDarkMode(){
        viewModelScope.launch {
            dataStoreSettings.getDarkMode.collect { result ->
                _switchState.value = result ?: false
            }
        }
    }

    fun toggleDarkMode(state: Boolean) {
        viewModelScope.launch {
            _switchState.value = !switchState.value
            dataStoreSettings.saveDarkMode(state)
        }
    }
}