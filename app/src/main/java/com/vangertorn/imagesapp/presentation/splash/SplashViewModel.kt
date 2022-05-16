package com.vangertorn.imagesapp.presentation.splash

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.data.network.state.NetworkState
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.usecase.FirstStartUseCase
import com.vangertorn.imagesapp.util.extension.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firstStartUseCase: FirstStartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun checkDataExist() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                firstStartUseCase.execute()
                _uiState.value = UiState.Loaded
            } catch (error: Exception) {
                _uiState.value = UiState.Error(ExceptionParser.getMessage(error))
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

