package com.vangertorn.imagesapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentSplashBinding
import com.vangertorn.imagesapp.util.SupportFragmentInset
import com.vangertorn.imagesapp.util.extension.goneUnless
import com.vangertorn.imagesapp.util.extension.launchWhenStarted
import com.vangertorn.imagesapp.util.extension.setVerticalMargin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SplashFragment : SupportFragmentInset<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewBinding: FragmentSplashBinding by viewBinding()
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.btnRetry.setVerticalMargin(marginBottom = bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        viewModel.checkDataExist()
        viewModel.state
            .onEach(::handleViewState)
            .launchWhenStarted(viewLifecycleOwner)

        viewBinding.btnRetry.setOnClickListener {
            viewModel.checkDataExist()
        }
    }

    private fun handleViewState(state: SplashViewModel.UiState) {

        when (state) {
            SplashViewModel.UiState.Loaded -> onLoaded()
            is SplashViewModel.UiState.Error -> showError(state.message)
            SplashViewModel.UiState.Empty -> {}
            SplashViewModel.UiState.Loading -> onLoading()
        }
    }

    private fun onLoading() {
        viewBinding.errorState.goneUnless(false)
        viewBinding.progressBar.goneUnless(true)
    }

    private fun onLoaded() {
        viewBinding.progressBar.goneUnless(false)
        viewBinding.errorState.goneUnless(false)
        navController.navigate(SplashFragmentDirections.toHome())
    }

    private fun showError(@StringRes stringRes: Int) {
        viewBinding.progressBar.goneUnless(false)
        viewBinding.errorMessage.text = requireContext().getString(stringRes)
        viewBinding.errorState.goneUnless(true)
    }
}
