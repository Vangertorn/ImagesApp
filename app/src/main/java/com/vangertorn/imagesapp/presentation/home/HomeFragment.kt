package com.vangertorn.imagesapp.presentation.home

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentHomeBinding
import com.vangertorn.imagesapp.util.SupportFragmentInset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : SupportFragmentInset<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}