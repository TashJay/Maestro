package com.example.ui.driver

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.components.LogoPlaceholder
import com.example.ui.components.TeddyButton
import com.example.ui.components.TeddyLoader
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ─── Splash ───────────────────────────────────────────────────────────────────

@Composable
fun DriverSplashScreen(onDone: () -> Unit) {
    val alpha by animateFloatAsState(targetValue = 1f, animationSpec = tween(800, easing = EaseIn), label = "fade")
    LaunchedEffect(Unit) { delay(2500); onDone() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoPlaceholder(size = 108.dp, modifier = Modifier.alpha(alpha))
            Spacer(Modifier.height(28.dp))
            Text("TEDDY DRIVER", color = TcGold, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 6.sp, modifier = Modifier.alpha(alpha))
            Spacer(Modifier.height(8.dp))
            Text("Drive. Earn. Thrive.", color = TcTextMuted, fontSize = 14.sp, letterSpacing = 1.sp, modifier = Modifier.alpha(alpha))
        }
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 56.dp), contentAlignment = Alignment.BottomCenter) {
            TeddyLoader()
        }
    }
}

// ─── Onboarding ───────────────────────────────────────────────────────────────

private data class DriverSlide(val emoji: String, val title: String, val subtitle: String)
private val driverSlides = listOf(
    DriverSlide("💰", "Earn on your schedule", "Set your own hours. Work mornings, evenings, or weekends — it's entirely up to you."),
    DriverSlide("📡", "Accept rides instantly", "Real-time requests delivered straight to your phone. No phone calls, no dispatch."),
    DriverSlide("🚀", "On the road in 24 hours", "Upload your documents and we'll have you verified and earning within one day.")
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DriverOnboardingScreen(onGetStarted: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { driverSlides.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.End) {
            if (pagerState.currentPage < driverSlides.lastIndex) {
                Text("Skip", color = TcTextMuted, fontSize = 14.sp, modifier = Modifier.clickable { onGetStarted() })
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val slide = driverSlides[page]
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(140.dp).background(TcGoldFaint, RoundedCornerShape(40.dp)).border(1.dp, TcGold.copy(0.2f), RoundedCornerShape(40.dp)),
                    contentAlignment = Alignment.Center
                ) { Text(slide.emoji, fontSize = 60.sp) }
                Spacer(Modifier.height(48.dp))
                Text(slide.title, color = TcTextPrimary, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, lineHeight = 34.sp)
                Spacer(Modifier.height(16.dp))
                Text(slide.subtitle, color = TcTextMuted, fontSize = 16.sp, textAlign = TextAlign.Center, lineHeight = 24.sp)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.Center) {
            repeat(driverSlides.size) { i ->
                Box(
                    modifier = Modifier.padding(horizontal = 4.dp).size(if (pagerState.currentPage == i) 24.dp else 6.dp, 6.dp)
                        .clip(CircleShape).background(if (pagerState.currentPage == i) TcGold else TcBorder)
                )
            }
        }
        if (pagerState.currentPage == driverSlides.lastIndex) TeddyButton("Start Driving", onGetStarted)
        else TeddyButton("Next") { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = tween(400)) } }
        Spacer(Modifier.height(40.dp))
    }
}
