package com.example.weather

import com.google.gson.annotations.SerializedName

// ---- Geocoding response (city name -> lat/lon) ----
data class GeocodingResponse(
    @SerializedName("results") val results: List<GeocodingResult>?
)

data class GeocodingResult(
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("country") val country: String?
)

// ---- Forecast response (lat/lon -> current + daily weather) ----
data class WeatherResponse(
    @SerializedName("current") val current: CurrentWeather,
    @SerializedName("daily") val daily: DailyForecast
)

data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("relative_humidity_2m") val humidity: Double,
    @SerializedName("wind_speed_10m") val windSpeed: Double,
    @SerializedName("weather_code") val weatherCode: Int
)

data class DailyForecast(
    @SerializedName("time") val dates: List<String>,
    @SerializedName("temperature_2m_max") val maxTemps: List<Double>,
    @SerializedName("temperature_2m_min") val minTemps: List<Double>
)

data class ForecastDayItem(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double
)
