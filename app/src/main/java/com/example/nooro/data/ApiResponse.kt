package com.example.nooro.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val location: Location? = Location(),
    val current: Current? = Current()
)

data class Location(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    @SerializedName("tz_id")
    val tzId: String? = null,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int? = null,
    val localtime: String? = null
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("temp_c")
    val tempC: Double? = null,
    @SerializedName("temp_f")
    val tempF: Double? = null,
    @SerializedName("is_day")
    val isDay: Int? = null,
    @SerializedName("condition")
    val condition: Condition? = Condition(),
    @SerializedName("wind_mph")
    val windMph: Double? = null,
    @SerializedName("wind_kph")
    val windKph: Double? = null,
    @SerializedName("wind_degree")
    val windDegree: Int? = null,
    @SerializedName("wind_dir")
    val windDir: String? = null,
    @SerializedName("pressure_mb")
    val pressureMb: Int? = null,
    @SerializedName("pressure_in")
    val pressureIn: Double? = null,
    val humidity: Int? = null,
    val cloud: Int? = null,
    @SerializedName("feelslike_c")
    val feelslikeC: Double? = null,
    @SerializedName("feelslike_f")
    val feelslikeF: Double? = null,
    @SerializedName("windchill_c")
    val windchillC: Double? = null,
    @SerializedName("windchill_f")
    val windchillF: Double? = null,
    @SerializedName("heatindex_c")
    val heatindexC: Double? = null,
    @SerializedName("heatindex_f")
    val heatindexF: Double? = null,
    @SerializedName("dewpoint_f")
    val dewpointF: Double? = null,
    @SerializedName("vis_km") val
    visKm: Int? = null,
    @SerializedName("vis_miles")
    val visMiles: Int? = null,
    val uv: Double? = null,
    @SerializedName("gust_mph")
    val gustMph: Double? = null,
    @SerializedName("gust_kph")
    val gustKph: Double? = null
)

data class Condition(
    val text: String? = null,
    val icon: String? = null,
    val code: Int? = null
)