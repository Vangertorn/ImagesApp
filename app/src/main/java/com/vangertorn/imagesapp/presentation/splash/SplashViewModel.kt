package com.vangertorn.imagesapp.presentation.splash

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.data.network.state.NetworkState
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.usecase.GetImageUseCase
import com.vangertorn.imagesapp.util.extension.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val networkState: NetworkState
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun getImage() {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = getImageUseCase.execute()
                _uiState.value = UiState.Loaded(result.first())
            } catch (error: Exception) {
                _uiState.value = UiState.Error(ExceptionParser.getMessage(error))
            }

            // _uiState.value =  UiState.Error(R.string.error_internet_connection)
        }
    }

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    class Loaded(val itemState: ImageModel) : UiState()
    class Error(@StringRes val message: Int) : UiState()
}
}

