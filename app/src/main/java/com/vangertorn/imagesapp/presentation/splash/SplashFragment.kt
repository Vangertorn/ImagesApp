package com.vangertorn.imagesapp.presentation.splash

import androidx.fragment.app.viewModels
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.FragmentSplashBinding
import com.vangertorn.imagesapp.util.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : SupportFragmentInset<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewBinding: FragmentSplashBinding by viewBinding()
    private val viewModel: SplashViewModel by viewModels()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}