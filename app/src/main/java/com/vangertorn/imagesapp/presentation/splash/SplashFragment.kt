package com.vangertorn.imagesapp.presentation.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentSplashBinding
import com.vangertorn.imagesapp.util.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vangertorn.imagesapp.GlideApp
import com.vangertorn.imagesapp.domain.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : SupportFragmentInset<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewBinding: FragmentSplashBinding by viewBinding()
    private val viewModel: SplashViewModel by viewModels()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImage()

        fetchPraySchedules()
    }

    private fun fetchPraySchedules() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is SplashViewModel.UiState.Loaded -> onLoaded(state.itemState)
                    is SplashViewModel.UiState.Error -> showError(state.message)
                    else -> showLoading()
                }
            }
        }
    }

    private fun onLoaded(image: ImageModel) {
        image.run {
            GlideApp
                .with(requireContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(viewBinding.image)
        }
    }

    private fun showLoading() {
        Timber.d("showLoading")
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}