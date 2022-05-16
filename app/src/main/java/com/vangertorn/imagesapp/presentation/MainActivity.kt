package com.vangertorn.imagesapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.ActivityMainBinding
import com.vangertorn.imagesapp.util.SupportActivityInset
import com.vangertorn.imagesapp.util.setWindowTransparency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SupportActivityInset<ActivityMainBinding>() {

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }
    private val navController: NavController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setWindowTransparency(this)
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            navController.popBackStack()
        }
    }

    override fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }
}
