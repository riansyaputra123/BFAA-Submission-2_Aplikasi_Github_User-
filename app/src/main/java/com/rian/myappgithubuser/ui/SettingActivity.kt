package com.rian.myappgithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.rian.myappgithubuser.R
import com.rian.myappgithubuser.databinding.ActivitySettingBinding
import com.rian.myappgithubuser.settings.SettingPreferences
import com.rian.myappgithubuser.settings.dataStore
import com.rian.myappgithubuser.viewmodel.SettingViewModel
import com.rian.myappgithubuser.viewmodel.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySettingBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySettingBinding.inflate(layoutInflater)

            val switchTheme = binding.switchTheme

            val pref = SettingPreferences.getInstance(dataStore)

            val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
                SettingViewModel::class.java
            )

            settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }

            setContentView(binding.root)
            binding.btnBack.setOnClickListener {
                finish()
            }
        }
}