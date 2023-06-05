package com.lucanicoletti.data.repository

import com.lucanicoletti.data.WeatherApi
import com.lucanicoletti.data.entity.CurrentWeather
import com.lucanicoletti.data.entity.CurrentWeatherResponse
import com.lucanicoletti.domain.model.WeatherModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Date

class WeatherRepositoryImplTest {

    private val weatherApi: WeatherApi = mockk()
    private val weatherRepositoryImpl = WeatherRepositoryImpl(weatherApi)

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 45, 48, 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 71, 73, 75, 77, 80, 81, 82, 85, 86, 95, 99])
    fun `GIVEN valid response WHEN repository calls THEN correct mapping`(weatherCode: Int) =
        runTest(UnconfinedTestDispatcher()) {
            // GIVEN
            val lat = 55f
            val lng = 56f
            val expectedResult = WeatherModel(
                temperature = 24.0,
                windSpeed = 12.0,
                windDirection = 90,
                weatherDescription = WeatherRepositoryImpl.mapWeatherCodeToDescription[weatherCode]!!,
                isDay = 1,
                time = "14:00:00",
            )
            coEvery {
                weatherApi.getCurrentWeather(lat, lng)
            } returns CurrentWeatherResponse(
                latitude = lat.toDouble(),
                longitude = lng.toDouble(),
                generationTimeMs = Date().time.toDouble(),
                0,
                timezone = "GMT + 0",
                timezoneAbbreviation = "0",
                elevation = 45,
                currentWeather = CurrentWeather(
                    temperature = 24.0,
                    windSpeed = 12.0,
                    windDirection = 90,
                    weatherCode = weatherCode,
                    isDay = 1,
                    time = "14:00:00"
                )
            )

            // WHEN
            val result = weatherRepositoryImpl.getCurrentWeather(lat, lng)!!

            // THEN
            assertEquals(expectedResult, result)
        }
}