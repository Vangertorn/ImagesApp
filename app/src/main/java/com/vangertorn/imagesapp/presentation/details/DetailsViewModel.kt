package com.vangertorn.imagesapp.presentation.details

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.usecase.GetDetailsUseCase
import com.vangertorn.imagesapp.util.extension.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageId = requireNotNull(
        savedStateHandle.get<String>("imageId")
    ) {
        "DetailsFragment requires argument `imageId` of type String"
    }

    private val _state = MutableStateFlow<UiState>(UiState.Empty)
    val state: StateFlow<UiState> = _state

    fun getDetails() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val image = getDetailsUseCase.execute(imageId)
                _state.value = UiState.Loaded(image)
            } catch (error: Exception) {
                _state.value = UiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Loaded(val image: ImageModel) : UiState()
        data class Error(@StringRes val message: Int) : UiState()
    }
}