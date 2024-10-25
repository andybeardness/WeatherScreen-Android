package andy.beardness.weatherscreen.domain

data class HourEntity(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val temperature: Double,
    val weather: WeatherEntity,
)

enum class WeatherEntity {
    ClearSky,
    MainlyClear,
    PartlyCloudy,
    Overcast,
    Fog,
    RimeFog,
    DrizzleLight,
    DrizzleModerate,
    DrizzleDense,
    FreezingDrizzleLight,
    FreezingDrizzleDense,
    RainSlight,
    RainModerate,
    RainHeavy,
    FreezingRainLight,
    FreezingRainHeavy,
    SnowSlight,
    SnowModerate,
    SnowHeavy,
    SnowGrains,
    RainShowersSlight,
    RainShowersModerate,
    RainShowersViolent,
    SnowShowersSlight,
    SnowShowersHeavy,
    Thunderstorm,
    ThunderstormHailSlight,
    ThunderstormHailHeavy,
    Unknown;

    companion object {
        fun fromWmoWeatherCode(code: Int): WeatherEntity {
            return when (code) {
                0 -> ClearSky

                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast

                45 -> Fog
                46 -> RimeFog

                51 -> DrizzleLight
                53 -> DrizzleModerate
                55 -> DrizzleDense

                56 -> FreezingDrizzleLight
                57 -> FreezingDrizzleDense

                61 -> RainSlight
                63 -> RainModerate
                65 -> RainHeavy

                66 -> FreezingRainLight
                67 -> FreezingRainHeavy

                71 -> SnowSlight
                73 -> SnowModerate
                75 -> SnowHeavy

                77 -> SnowGrains

                80 -> RainShowersSlight
                81 -> RainShowersModerate
                82 -> RainShowersViolent

                85 -> SnowShowersSlight
                86 -> SnowShowersHeavy

                95 -> Thunderstorm

                96 -> ThunderstormHailSlight
                99 -> ThunderstormHailHeavy

                else -> Unknown
            }
        }
    }
}