package com.ardianhilmip.catcares

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ardianhilmip.catcares.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        initUI()
    }

    private fun initUI() {
        val navHostBottomBar =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController
        binding?.bottomNavigation?.setupWithNavController(navControllerBottomBar)
        navControllerBottomBar.addOnDestinationChangedListener { _, currentDestination, _ ->
            if (isMainPage(currentDestination)) {
                binding?.apply {
                    bottomAppBar.visibility = View.VISIBLE
                    fabCamera.visibility = View.VISIBLE
                }
            } else {
                binding?.apply {
                    bottomAppBar.visibility = View.GONE
                    fabCamera.visibility = View.GONE
                }
            }
        }

        binding?.fabCamera?.setOnClickListener {
            navControllerBottomBar.navigate(R.id.detectionFragment)
        }
    }


    private fun isMainPage(currentDestination: NavDestination): Boolean {
        return currentDestination.id == R.id.homeFragment
                || currentDestination.id == R.id.checkUpFragment
                || currentDestination.id == R.id.articleFragment
                || currentDestination.id == R.id.profileFragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}