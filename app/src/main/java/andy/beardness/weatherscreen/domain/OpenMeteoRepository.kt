package andy.beardness.weatherscreen.domain

import andy.beardness.weatherscreen.data.OpenMeteoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://api.open-meteo.com/v1/"

private const val LATITUDE: Double = 41.64
private const val LONGITUDE: Double = 41.64
private const val HOURLY: String = "temperature_2m,weather_code"
private const val FORECAST_DAYS: Int = 2

private const val DATE_TIME_SEPARATOR = "T"
private const val YEAR_MOUTH_DAY = "-"
private const val HOUR_MINUTE_SEPARATOR = ":"

class OpenMeteoRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val openMeteoService = retrofit.create(OpenMeteoService::class.java)

    suspend fun hours(): List<HourEntity> {
        val forecast =  openMeteoService.forecast(
            latitude = LATITUDE,
            longitude = LONGITUDE,
            hourly = HOURLY,
            forecastDays = FORECAST_DAYS,
        )

        val hourlySize = forecast.hourly.time.size

        return (0..<hourlySize)
            .map { index ->
                val dateTimeSplit = forecast.hourly.time[index].split(DATE_TIME_SEPARATOR)
                val dateString = dateTimeSplit[0]
                val timeString = dateTimeSplit[1]

                val yearMountDaySplit = dateString.split(YEAR_MOUTH_DAY)
                val year = yearMountDaySplit[0].toInt()
                val month = yearMountDaySplit[1].toInt()
                val day = yearMountDaySplit[2].toInt()

                val hourMinuteSplit = timeString.split(HOUR_MINUTE_SEPARATOR)
                val hour = hourMinuteSplit[0].toInt()

                val temperature = forecast.hourly.temperature2m[index]

                val weatherCode = forecast.hourly.weatherCode[index]
                val weather = WeatherEntity.fromWmoWeatherCode(weatherCode)

                HourEntity(
                    year = year,
                    month = month,
                    day = day,
                    hour = hour,
                    temperature = temperature,
                    weather = weather,
                )
            }
    }
}