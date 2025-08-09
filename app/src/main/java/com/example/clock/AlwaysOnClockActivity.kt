package com.example.clock

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class AlwaysOnClockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

        setContent {
            AlwaysOnClockResponsiveScreen()
        }
    }
}

@Composable
fun AlwaysOnClockResponsiveScreen() {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val fontSize = (maxWidth.value * 0.48).sp
            ClockText(fontSize = fontSize)
        }
    }
}

@Composable
fun ClockText(fontSize: TextUnit) {
    var time by remember { mutableStateOf(currentTimeString()) }

    LaunchedEffect(Unit) {
        while (true) {
            val now = System.currentTimeMillis()
            val delayMs = 60000 - (now % 60000)
            delay(delayMs)
            time = currentTimeString()
        }
    }

    Text(
        text = time,
        color = Color.White,
        fontSize = fontSize,
//        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
        maxLines = 1
    )
}

fun currentTimeString(): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(Date())
}