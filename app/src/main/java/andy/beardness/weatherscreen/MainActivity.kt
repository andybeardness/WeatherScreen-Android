package andy.beardness.weatherscreen

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import andy.beardness.weatherscreen.ui.theme.WeatherScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                    or  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        enableEdgeToEdge()
        setContent {
            WeatherScreenTheme {
                Screen()
            }
        }
    }
}

@Composable
fun Screen() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "здарова, заебал", color = color, style = TextStyle(fontSize = 80.sp), textAlign = TextAlign.Center)
    }
}

@Composable
@Preview(widthDp = 16*10, heightDp = 9*10)
fun Preview_Screen() {
    Screen()
}