package com.ardianhilmip.catcares

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ardianhilmip.catcares.data.SettingPreference
import com.ardianhilmip.catcares.data.factory.SettingModelFactory
import com.ardianhilmip.catcares.databinding.ActivityMainBinding
import com.ardianhilmip.catcares.view.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        initUI()

        val pref = SettingPreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
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
                || currentDestination.id == R.id.catFragment
                || currentDestination.id == R.id.articleFragment
                || currentDestination.id == R.id.profileFragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}