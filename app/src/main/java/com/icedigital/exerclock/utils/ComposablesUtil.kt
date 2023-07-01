package com.icedigital.exerclock.utils

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.icedigital.exerclock.R
import java.util.concurrent.TimeUnit


@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    imageResource: Int
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = imageResource).apply(block = {
                size(Size.ORIGINAL)
            }).build(),
            imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth()
    )
}
@Composable
fun TimerCircle(time: Long, isTimerRunning: Boolean, totalTime: Long) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(130.dp)
    ) {
        CircularProgressIndicator(
            progress = 1f - (time.toFloat() / totalTime),
            strokeWidth = 6.dp,
            modifier = Modifier
                .size(110.dp)
                .alpha(if (isTimerRunning) 1f else 0f),
            color = MaterialTheme.colors.primary
        )
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = 6.dp,
            modifier = Modifier
                .size(110.dp)
                .alpha(0.5f),
            color = MaterialTheme.colors.primary
        )
        Text(
            text = formatTime(time, totalTime),
            style = TextStyle(fontSize = 24.sp, color = Color.Black)
        )
    }
}

@Composable
fun formatTime(timeInMillis: Long, totalTime: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}
