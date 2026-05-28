package com.example.ui.passenger

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.components.TeddyButton
import com.example.ui.components.TeddyOutlineButton
import com.example.ui.theme.*
import kotlinx.coroutines.launch

private data class OnboardSlide(val emoji: String, val title: String, val subtitle: String)

private val passengerSlides = listOf(
    OnboardSlide("📍", "Always know where you are", "Watch your driver's location in real time. No more wondering when they'll arrive."),
    OnboardSlide("⚡", "Book in under 30 seconds", "Enter your destination, choose your ride type, and you're set. That simple."),
    OnboardSlide("💳", "Pay the way you prefer", "M-Pesa, card or cash — pick what works. Receipts sent instantly to your phone.")
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PassengerOnboardingScreen(onGetStarted: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { passengerSlides.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.End) {
            if (pagerState.currentPage < passengerSlides.lastIndex) {
                Text("Skip", color = TcTextMuted, fontSize = 14.sp, modifier = Modifier.clickable { onGetStarted() })
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val slide = passengerSlides[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(TcGoldFaint, RoundedCornerShape(40.dp))
                        .border(1.dp, TcGold.copy(0.2f), RoundedCornerShape(40.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(slide.emoji, fontSize = 60.sp)
                }
                Spacer(Modifier.height(48.dp))
                Text(
                    slide.title, color = TcTextPrimary, fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, lineHeight = 34.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    slide.subtitle, color = TcTextMuted, fontSize = 16.sp,
                    textAlign = TextAlign.Center, lineHeight = 24.sp
                )
            }
        }

        // Page indicators
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.Center) {
            repeat(passengerSlides.size) { i ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (pagerState.currentPage == i) 24.dp else 6.dp, 6.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == i) TcGold else TcBorder)
                )
            }
        }

        if (pagerState.currentPage == passengerSlides.lastIndex) {
            TeddyButton("Get Started", onGetStarted)
        } else {
            TeddyButton("Next") {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = tween(400)) }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}
