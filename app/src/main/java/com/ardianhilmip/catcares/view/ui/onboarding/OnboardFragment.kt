package com.ardianhilmip.catcares.view.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.databinding.FragmentOnboardBinding
import com.ardianhilmip.catcares.view.adapter.ViewPagerAdapter
import com.ardianhilmip.catcares.view.ui.onboarding.screen.FirstFragment
import com.ardianhilmip.catcares.view.ui.onboarding.screen.SecondFragment
import com.ardianhilmip.catcares.view.ui.onboarding.screen.ThirdFragment

class OnboardFragment : Fragment() {
    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )

        binding?.apply {
            fragment = this@OnboardFragment
            viewPager.adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            wormDotsIndicator.setViewPager2(viewPager2 = viewPager)
            toLogin.setOnClickListener {
                findNavController().navigate(OnboardFragmentDirections.actionOnboardFragmentToLoginFragment())
                onBoardingFinished()
            }
            toRegis.setOnClickListener {
                findNavController().navigate(OnboardFragmentDirections.actionOnboardFragmentToRegisterFragment())
                onBoardingFinished()
            }
        }

        viewPager2Listener()

        return binding?.root
    }

    private fun viewPager2Listener() {
        binding?.viewPager?.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.apply {
                    if (position == 2){
                        btnNext.visibility = View.INVISIBLE
                        wormDotsIndicator.visibility = View.INVISIBLE
                        toLogin.visibility = View.VISIBLE
                        toRegis.visibility = View.VISIBLE
                    } else {
                        wormDotsIndicator.visibility = View.VISIBLE
                        btnNext.visibility = View.VISIBLE
                        toLogin.visibility = View.INVISIBLE
                        toRegis.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    fun nextButtonClicked(){
        binding?.apply {
            viewPager.currentItem++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}