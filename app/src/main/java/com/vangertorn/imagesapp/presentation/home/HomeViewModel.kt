package com.vangertorn.imagesapp.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.usecase.ChangeFavoriteUseCase
import com.vangertorn.imagesapp.domain.usecase.GetImagesUseCase
import com.vangertorn.imagesapp.presentation.home.adapter.ImageAdapter
import com.vangertorn.imagesapp.presentation.splash.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
) : ViewModel(), ImageAdapter.Callbacks {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val images = getImagesUseCase.execute()
            _uiState.emit(UiState.Loaded(images))
        }
    }

    override fun onItemClicked(imageId: String) {
        viewModelScope.launch {
            _uiState.emit(UiState.NavigateToDetails(imageId))
        }
    }

    override fun onFavoriteClicked(imageId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val images = changeFavoriteUseCase.execute(imageId, isFavorite)
            _uiState.emit(UiState.Loaded(images))
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Loaded(val images: List<ImageModel>) : UiState()
        data class Error(@StringRes val message: Int) : UiState()
        data class NavigateToDetails(val imageId: String) : UiState()
    }
}