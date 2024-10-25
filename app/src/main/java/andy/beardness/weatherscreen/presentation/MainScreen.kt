package andy.beardness.weatherscreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(state: MainScreenViewState, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is MainScreenViewState.Content -> MainScreenContent(state)
            is MainScreenViewState.Loading -> MainScreenLoading()
            is MainScreenViewState.Error -> MainScreenError()
        }
    }
}

@Composable
fun MainScreenContent(state: MainScreenViewState.Content) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = state.now.status.icon,
                contentDescription = null,
                tint = Color.White,
            )
            Text(
                text = "${state.now.temperature} °C",
                color = Color.White,
                fontSize = 64.sp,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.next.forEach { hour ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        imageVector = hour.status.icon,
                        contentDescription = null,
                        tint = Color.White,
                    )
                    Spacer(
                        modifier = Modifier.width(24.dp)
                    )
                    Text(
                        text = "${hour.temperature} °C",
                        color = Color.White,
                        fontSize = 48.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreenLoading() {
    CircularProgressIndicator(
        color = Color.White,
    )
}

@Composable
fun MainScreenError() {

}