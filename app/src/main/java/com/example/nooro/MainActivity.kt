package com.example.nooro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.nooro.ui.screen.CITY_KEY
import com.example.nooro.ui.screen.SHARED_PREF_NAME
import com.example.nooro.ui.screen.SearchTopBar
import com.example.nooro.ui.screen.WeatherView
import com.example.nooro.ui.theme.NooroTheme
import com.example.nooro.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NooroTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SearchTopBar(
                            viewModel = viewModel,
                            sharedPreferences = sharedPreferences
                        )
                    }) { innerPadding ->
                    WeatherView(
                        modifier = Modifier.padding(innerPadding),
                        results = viewModel.resultFlow
                    )
                }

                val cityName = sharedPreferences.getString(CITY_KEY, "") ?: ""
                viewModel.onTextChange(cityName)
            }
        }
    }
}
