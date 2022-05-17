package com.vangertorn.imagesapp.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.data.network.state.NetworkState
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.usecase.ChangeFavoriteUseCase
import com.vangertorn.imagesapp.domain.usecase.GetImagesUseCase
import com.vangertorn.imagesapp.domain.usecase.GetRandomImagesUseCase
import com.vangertorn.imagesapp.presentation.home.adapter.ImageAdapter
import com.vangertorn.imagesapp.presentation.splash.SplashViewModel
import com.vangertorn.imagesapp.util.extension.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val getRandomImagesUseCase: GetRandomImagesUseCase,
    private val networkState: NetworkState
) : ViewModel(), ImageAdapter.Callbacks {

    private val _state = MutableStateFlow<UiState>(UiState.Empty)
    val state: StateFlow<UiState> = _state

    private val _sideEffects = MutableSharedFlow<SideEffect>()
    val sideEffects: SharedFlow<SideEffect> = _sideEffects

    val internetState: StateFlow<Boolean> = networkState.connectedFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        networkState.isConnected()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val images = getImagesUseCase.execute()
            _state.emit(UiState.Loaded(images))
        }
    }

    override fun onItemClicked(imageId: String) {
        viewModelScope.launch {
            _sideEffects.emit(SideEffect.NavigateToDetails(imageId))
        }
    }

    override fun onFavoriteClicked(imageId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val images = changeFavoriteUseCase.execute(imageId, isFavorite)
            _state.emit(UiState.Loaded(images))
        }
    }

    fun onUpdateClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            try {
                val image = getRandomImagesUseCase.execute()
                image?.let {
                    _state.value = UiState.Loaded(it)
                } ?: run { _state.value = UiState.Error(R.string.error_empty_list) }
            } catch (error: Exception) {
                _state.value = UiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Loaded(val images: List<ImageModel>) : UiState()
        data class LoadedFavorite(val images: List<ImageModel>) : UiState()
        data class Error(@StringRes val message: Int) : UiState()
    }

    sealed class SideEffect {
        data class NavigateToDetails(val imageId: String) : SideEffect()
    }
}