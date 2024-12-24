package com.example.nooro.di

import com.example.nooro.api.WeatherApi
import com.example.nooro.data.ApiResponse
import com.example.nooro.utils.ApiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val openWeatherApi: WeatherApi) {

    suspend fun getWeather(query: String): ApiState<ApiResponse> = runCatching {
        openWeatherApi.getWeather(q = query)
    }.map {resp ->
        if (resp.isSuccessful) {
            resp.body()?.let {
                ApiState.Success(it)
            }
        } else {
            resp.errorBody().use { err ->
                ApiState.Failure(err?.string() ?: "some Error")
            }
        }
    }.mapCatching {
        requireNotNull(it)
    }.getOrElse { e ->
        ApiState.Failure(e.message.toString())
    }
}