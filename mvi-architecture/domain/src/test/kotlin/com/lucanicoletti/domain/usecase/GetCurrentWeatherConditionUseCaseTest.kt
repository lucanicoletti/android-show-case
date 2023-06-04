package com.lucanicoletti.domain.usecase

import com.lucanicoletti.domain.model.WeatherModel
import com.lucanicoletti.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetCurrentWeatherConditionUseCaseTest {
    private val weatherRepository: WeatherRepository = mockk()
    private val useCase = GetCurrentWeatherConditionUseCase(weatherRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN valid response WHEN invoking UseCase THEN correct answer`() =
        runTest(UnconfinedTestDispatcher()) {
            // GIVEN
            val lat = 55f
            val lng = 56f
            val expectedResult = WeatherModel(
                temperature = 24.0,
                windSpeed = 12.0,
                windDirection = 90,
                weatherDescription = "Overcast",
                isDay = 1,
                time = "14:00:00",
            )
            coEvery {
                weatherRepository.getCurrentWeather(lat, lng)
            } returns WeatherModel(
                temperature = 24.0,
                windSpeed = 12.0,
                windDirection = 90,
                weatherDescription = "Overcast",
                isDay = 1,
                time = "14:00:00"
            )

            // WHEN
            val result = useCase(lat, lng)!!

            // THEN
            assertEquals(expectedResult, result)
        }
}