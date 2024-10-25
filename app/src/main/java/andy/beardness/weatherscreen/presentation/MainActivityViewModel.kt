package andy.beardness.weatherscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import andy.beardness.weatherscreen.domain.OpenMeteoRepository
import andy.beardness.weatherscreen.domain.WeatherEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.roundToInt

class MainActivityViewModel(
    private val openMeteoRepository: OpenMeteoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenViewState>(value = MainScreenViewState.Loading)
    val state: StateFlow<MainScreenViewState> = _state

    init {
        viewModelScope.launch {
            val hours = openMeteoRepository.hours()

            val timeNowInMillis = System.currentTimeMillis()

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeNowInMillis

            val nowYear = calendar[Calendar.YEAR]
            val nowMonth = calendar[Calendar.MONTH] + 1
            val nowDay = calendar[Calendar.DATE]
            val nowHour = calendar[Calendar.HOUR_OF_DAY]

            val startIndex = hours.indexOfFirst { hour ->
                hour.year == nowYear
                        && hour.month == nowMonth
                        && hour.day == nowDay
                        && hour.hour == nowHour
            }

            val endIndex = startIndex + 6

            val weatherHours =
                hours.slice(startIndex..<endIndex)
                    .map { hour ->
                        val status = when (hour.weather) {
                            WeatherEntity.ClearSky -> WeatherStatus.Sunny
                            WeatherEntity.MainlyClear -> WeatherStatus.Cloud
                            WeatherEntity.PartlyCloudy -> WeatherStatus.Cloud
                            WeatherEntity.Overcast -> WeatherStatus.Cloud
                            WeatherEntity.Fog -> WeatherStatus.Cloud
                            WeatherEntity.RimeFog -> WeatherStatus.Cloud
                            WeatherEntity.DrizzleLight -> WeatherStatus.Rain
                            WeatherEntity.DrizzleModerate -> WeatherStatus.Rain
                            WeatherEntity.DrizzleDense -> WeatherStatus.Rain
                            WeatherEntity.FreezingDrizzleLight -> WeatherStatus.Rain
                            WeatherEntity.FreezingDrizzleDense -> WeatherStatus.Rain
                            WeatherEntity.RainSlight -> WeatherStatus.Rain
                            WeatherEntity.RainModerate -> WeatherStatus.Rain
                            WeatherEntity.RainHeavy -> WeatherStatus.Rain
                            WeatherEntity.FreezingRainLight -> WeatherStatus.Rain
                            WeatherEntity.FreezingRainHeavy -> WeatherStatus.Rain
                            WeatherEntity.SnowSlight -> WeatherStatus.Snow
                            WeatherEntity.SnowModerate -> WeatherStatus.Snow
                            WeatherEntity.SnowHeavy -> WeatherStatus.Snow
                            WeatherEntity.SnowGrains -> WeatherStatus.Snow
                            WeatherEntity.RainShowersSlight -> WeatherStatus.Rain
                            WeatherEntity.RainShowersModerate -> WeatherStatus.Rain
                            WeatherEntity.RainShowersViolent -> WeatherStatus.Rain
                            WeatherEntity.SnowShowersSlight -> WeatherStatus.Snow
                            WeatherEntity.SnowShowersHeavy -> WeatherStatus.Snow
                            WeatherEntity.Thunderstorm -> WeatherStatus.Thunderstorm
                            WeatherEntity.ThunderstormHailSlight -> WeatherStatus.Thunderstorm
                            WeatherEntity.ThunderstormHailHeavy -> WeatherStatus.Thunderstorm
                            WeatherEntity.Unknown -> WeatherStatus.Unknown
                        }

                        WeatherHour(
                            status = status,
                            temperature = hour.temperature.roundToInt(),
                        )
                    }

            val now = weatherHours.first()
            val next = weatherHours.slice(1..<(6))

            _state.emit(
                MainScreenViewState.Content(
                    now = now,
                    next = next,
                )
            )
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val openMeteoRepository = OpenMeteoRepository()
                return MainActivityViewModel(openMeteoRepository = openMeteoRepository) as T
            }
        }
    }
}