package com.vangertorn.imagesapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vangertorn.imagesapp.GlideApp
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentHomeBinding
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.presentation.home.adapter.ImageAdapter
import com.vangertorn.imagesapp.presentation.splash.SplashFragmentDirections
import com.vangertorn.imagesapp.presentation.splash.SplashViewModel
import com.vangertorn.imagesapp.util.SupportFragmentInset
import com.vangertorn.imagesapp.util.extension.goneUnless
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        handleViewState()

        viewBinding.rvListImages.adapter = adapter
    }

    private fun handleViewState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeViewModel.UiState.Loaded -> onLoaded(state.images)
                    is HomeViewModel.UiState.Error -> {
                    }
                    HomeViewModel.UiState.Empty -> {}
                    HomeViewModel.UiState.Loading -> {}
                    is HomeViewModel.UiState.NavigateToDetails -> onNavigate(state.imageId)
                }
            }
        }
    }

    private fun onLoaded(images: List<ImageModel>) {
        adapter.submitList(images)
    }

    private fun onNavigate(imageId: String) {
        val bundle = Bundle()
        bundle.putString("imageId", imageId)
        navController.navigate(R.id.toDetails, bundle)
    }
}