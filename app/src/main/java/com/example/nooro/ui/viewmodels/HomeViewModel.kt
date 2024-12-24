package com.example.nooro.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nooro.api.WeatherApi
import com.example.nooro.data.ApiResponse
import com.example.nooro.di.WeatherRepository
import com.example.nooro.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _resultFlow = MutableStateFlow<ApiState<ApiResponse>>(ApiState.Loading)
    val resultFlow = _resultFlow.asStateFlow()

    fun onTextChange(query: String) {

        if (query.isEmpty()) {
            _resultFlow.value = ApiState.EmptyState
        } else {
            _resultFlow.value = ApiState.Loading
            viewModelScope.launch {
                val weather = weatherRepository.getWeather(query)
                _resultFlow.value = weather
            }
        }
    }
}