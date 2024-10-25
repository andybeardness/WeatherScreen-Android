package andy.beardness.weatherscreen.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material.icons.rounded.SevereCold
import androidx.compose.material.icons.rounded.Snowmobile
import androidx.compose.material.icons.rounded.Thunderstorm
import androidx.compose.material.icons.rounded.Train
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface MainScreenViewState {
    data object Loading : MainScreenViewState

    data object Error : MainScreenViewState

    data class Content(
        val now: WeatherHour,
        val next: List<WeatherHour>,
    ) : MainScreenViewState
}

data class WeatherHour(
    val status: WeatherStatus,
    val temperature: Int,
)

enum class WeatherStatus(
    val icon: ImageVector,
) {
    Cloud(icon = Icons.Rounded.Cloud),
    Rain(icon = Icons.Rounded.WaterDrop),
    Thunderstorm(icon = Icons.Rounded.Thunderstorm),
    Snow(icon = Icons.Rounded.SevereCold),
    Sunny(icon = Icons.Rounded.WbSunny),
    Unknown(icon = Icons.Rounded.QuestionMark);
}