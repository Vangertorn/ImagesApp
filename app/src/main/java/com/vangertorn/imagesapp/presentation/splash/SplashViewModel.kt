package com.vangertorn.imagesapp.presentation.splash

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.data.network.state.NetworkState
import com.vangertorn.imagesapp.domain.usecase.FirstStartUseCase
import com.vangertorn.imagesapp.util.extension.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firstStartUseCase: FirstStartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Empty)
    val state: StateFlow<UiState> = _state

    fun checkDataExist() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            try {
                firstStartUseCase.execute()
                _state.value = UiState.Loaded
            } catch (error: Exception) {
                _state.value = UiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        object Loaded : UiState()
        data class Error(@StringRes val message: Int) : UiState()
    }
}

