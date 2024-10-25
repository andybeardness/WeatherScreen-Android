package andy.beardness.weatherscreen.data

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {

    @GET("forecast")
    suspend fun forecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("forecast_days") forecastDays: Int,
    ): OpenMeteoResponse
}

