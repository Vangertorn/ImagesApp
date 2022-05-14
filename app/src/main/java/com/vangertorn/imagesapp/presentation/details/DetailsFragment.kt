package com.vangertorn.imagesapp.presentation.details

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentDetailsBinding
import com.vangertorn.imagesapp.util.SupportFragmentInset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : SupportFragmentInset<FragmentDetailsBinding>(R.layout.fragment_details) {

    override val viewBinding: FragmentDetailsBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}