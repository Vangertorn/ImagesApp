package com.vangertorn.imagesapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentHomeBinding
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.presentation.home.adapter.ImageAdapter
import com.vangertorn.imagesapp.util.SupportFragmentInset
import com.vangertorn.imagesapp.util.extension.goneUnless
import com.vangertorn.imagesapp.util.extension.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : SupportFragmentInset<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var adapter: ImageAdapter

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.rvListImages.setPadding(0, 0, 0, bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        adapter = ImageAdapter(viewModel)

        viewModel.internetState
            .onEach(::handleInternetState)
            .launchWhenStarted(viewLifecycleOwner)

        viewModel.sideEffects
            .onEach(::handleSideEffects)
            .launchWhenStarted(viewLifecycleOwner)

        viewModel.state
            .onEach(::handleViewState)
            .launchWhenStarted(viewLifecycleOwner)

        viewBinding.rvListImages.adapter = adapter

        viewBinding.btnFavorites.setOnClickListener {
            viewModel.onFavoriteListClicked()
        }

        viewBinding.btnUpdate.setOnClickListener {
            viewModel.onUpdateClicked()
        }
    }

    private fun handleSideEffects(sideEffect: HomeViewModel.SideEffect) {
        when (sideEffect) {
            is HomeViewModel.SideEffect.NavigateToDetails ->
                navController.navigate(HomeFragmentDirections.toDetails(sideEffect.imageId))
        }
    }

    private fun handleInternetState(isConnected: Boolean) {
        viewBinding.btnUpdate.isClickable = isConnected
    }

    private fun handleViewState(state: HomeViewModel.UiState) {
        when (state) {
            is HomeViewModel.UiState.Loaded -> onLoaded(state.images, state.isShowFavorite)
            is HomeViewModel.UiState.Error -> showError(state.message)
            HomeViewModel.UiState.Empty -> {}
            HomeViewModel.UiState.Loading -> onLoading()
        }
    }

    private fun onLoading() {
        viewBinding.errorState.goneUnless(false)
        viewBinding.progressBar.goneUnless(true)
        viewBinding.rvListImages.goneUnless(false)
    }

    private fun onLoaded(images: List<ImageModel>, isShowFavorite: Boolean) {
        adapter.submitList(images)
        val drawable = if (isShowFavorite) {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_favorite_active)
        } else {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_favorite_inactive)
        }
        viewBinding.btnFavorites.setImageDrawable(drawable)
        viewBinding.progressBar.goneUnless(false)
        viewBinding.errorState.goneUnless(false)
        viewBinding.rvListImages.goneUnless(true)
    }

    private fun showError(@StringRes stringRes: Int) {
        viewBinding.progressBar.goneUnless(false)
        viewBinding.errorMessage.text = requireContext().getString(stringRes)
        viewBinding.errorState.goneUnless(true)
    }
}
