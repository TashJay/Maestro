package com.example.ui.passenger

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.components.LogoPlaceholder
import com.example.ui.components.TeddyLoader
import com.example.ui.theme.TcGold
import com.example.ui.theme.TcTextMuted
import com.example.ui.theme.TcTextPrimary
import kotlinx.coroutines.delay

@Composable
fun PassengerSplashScreen(onDone: () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(800, easing = EaseIn),
        label = "fade"
    )
    LaunchedEffect(Unit) { delay(2500); onDone() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoPlaceholder(size = 108.dp, modifier = Modifier.alpha(alpha))
            Spacer(Modifier.height(28.dp))
            Text(
                "TEDDY RIDES",
                color = TcGold,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 6.sp,
                modifier = Modifier.alpha(alpha)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Your ride, your way.",
                color = TcTextMuted,
                fontSize = 14.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.alpha(alpha)
            )
        }
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 56.dp), contentAlignment = Alignment.BottomCenter) {
            TeddyLoader()
        }
    }
}
