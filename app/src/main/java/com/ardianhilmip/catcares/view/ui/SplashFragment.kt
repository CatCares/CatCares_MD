package com.ardianhilmip.catcares.view.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launchWhenResumed {
                if (pref.isLoggedIn) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                } else {
                    if (onBoardingFinish()) {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                    } else {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardFragment())
                    }
                }
            }
            return@postDelayed
        }, SPLASH_TIME_OUT)

        return binding?.root
    }

    private fun onBoardingFinish(): Boolean {
        val prefs: SharedPreferences =
            requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return prefs.getBoolean("Finished", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val SPLASH_TIME_OUT = 3000L
    }
}