package com.vangertorn.imagesapp.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.vangertorn.imagesapp.domain.model.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        class Loaded(val itemState: ImageModel) : UiState()
        class Error(@StringRes val message: Int) : UiState()
    }
}