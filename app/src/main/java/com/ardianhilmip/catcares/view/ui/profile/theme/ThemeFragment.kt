package com.ardianhilmip.catcares.view.ui.profile.theme

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.SettingPreference
import com.ardianhilmip.catcares.databinding.FragmentThemeBinding
import com.ardianhilmip.catcares.data.factory.SettingModelFactory
import com.ardianhilmip.catcares.view.viewmodel.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ThemeFragment : DialogFragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)

        val pref = SettingPreference.getInstance(context?.dataStore!!)
        val settingViewModel = ViewModelProvider(requireActivity(), SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(requireActivity()) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.switchTheme?.isChecked = false
            }
        }

        binding?.switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}