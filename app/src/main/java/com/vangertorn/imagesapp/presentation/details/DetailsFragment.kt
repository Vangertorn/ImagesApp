package com.vangertorn.imagesapp.presentation.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vangertorn.imagesapp.GlideApp
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentDetailsBinding
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.util.SupportFragmentInset
import com.vangertorn.imagesapp.util.extension.goneUnless
import com.vangertorn.imagesapp.util.extension.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : SupportFragmentInset<FragmentDetailsBinding>(R.layout.fragment_details) {

    override val viewBinding: FragmentDetailsBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.scrollContent.setPadding(0, 0, 0, bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel.getDetails()

        viewModel.state
            .onEach(::handleViewState)
            .launchWhenStarted(viewLifecycleOwner)

        viewBinding.btnRetry.setOnClickListener {
            viewModel.getDetails()
        }
        viewBinding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        viewBinding.ivShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, viewModel.getUri())
            startActivity(
                Intent.createChooser(intent, requireContext().getString(R.string.details_share))
            )
        }
    }

    private fun handleViewState(state: DetailsViewModel.UiState) {
        when (state) {
            is DetailsViewModel.UiState.Loaded -> onLoaded(state.image)
            is DetailsViewModel.UiState.Error -> showError(state.message)
            DetailsViewModel.UiState.Empty -> {}
            DetailsViewModel.UiState.Loading -> onLoading()
        }
    }

    private fun onLoaded(image: ImageModel) {
        viewBinding.progressBar.goneUnless(false)
        viewBinding.errorState.goneUnless(false)
        GlideApp
            .with(requireContext())
            .load(image.url)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(viewBinding.ivContent)
        viewBinding.tvHeight.text =
            requireContext().getString(R.string.details_height, image.height)
        viewBinding.tvWidth.text =
            requireContext().getString(R.string.details_width, image.width)
        viewBinding.successState.goneUnless(true)
    }

    private fun onLoading() {
        viewBinding.errorState.goneUnless(false)
        viewBinding.successState.goneUnless(false)
        viewBinding.progressBar.goneUnless(true)
    }

    private fun showError(@StringRes stringRes: Int) {
        viewBinding.progressBar.goneUnless(false)
        viewBinding.successState.goneUnless(false)
        viewBinding.errorMessage.text = requireContext().getString(stringRes)
        viewBinding.errorState.goneUnless(true)
    }
}
