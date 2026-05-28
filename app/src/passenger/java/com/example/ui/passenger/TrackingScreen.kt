package com.example.ui.passenger

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.TeddyButton
import com.example.ui.components.TeddyOutlineButton
import com.example.ui.theme.*
import kotlinx.coroutines.delay

private val tripStates = listOf("Finding Driver", "Driver En Route", "Driver Arrived", "On Trip", "Complete")

@Composable
fun PassengerTrackingScreen(onTripComplete: () -> Unit, onCancel: () -> Unit) {
    var stateIdx by remember { mutableIntStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulse by infiniteTransition.animateFloat(0.8f, 1f, infiniteRepeatable(tween(900), RepeatMode.Reverse), label = "p")

    LaunchedEffect(Unit) {
        delay(3000); stateIdx = 1
        delay(5000); stateIdx = 2
        delay(4000); stateIdx = 3
        delay(8000); stateIdx = 4
        delay(500); onTripComplete()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Status banner
        Box(
            modifier = Modifier.fillMaxWidth().background(if (stateIdx < 4) TcGoldFaint else TcSuccess.copy(0.15f)).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (stateIdx < 4) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = TcGold, strokeWidth = 2.dp, trackColor = TcGold.copy(0.2f))
                    Spacer(Modifier.width(10.dp))
                } else {
                    Icon(Icons.Default.CheckCircle, null, tint = TcSuccess, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                }
                Text(tripStates[stateIdx], color = if (stateIdx < 4) TcGold else TcSuccess, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }

        // Map placeholder
        Box(modifier = Modifier.fillMaxWidth().height(280.dp).background(TcSurface), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(if (stateIdx == 0) "🔍" else if (stateIdx < 3) "🚗" else if (stateIdx == 3) "🛣️" else "✅", fontSize = 52.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    when (stateIdx) {
                        0 -> "Searching for nearby drivers…"
                        1 -> "Driver is on the way"
                        2 -> "Driver has arrived"
                        3 -> "Enjoy your ride!"
                        else -> "You've arrived!"
                    },
                    color = TcTextMuted, fontSize = 13.sp
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
            if (stateIdx >= 1) {
                // Driver card
                Box(
                    modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(54.dp).background(TcElevated, CircleShape).border(2.dp, TcGold.copy(0.3f), CircleShape), contentAlignment = Alignment.Center) {
                            Text("JO", color = TcGold, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        }
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("James Otieno", color = TcTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(14.dp))
                                Text(" 4.92  ·  Toyota Wish  ·  KCA 456B", color = TcTextMuted, fontSize = 13.sp)
                            }
                        }
                        Box(modifier = Modifier.size(40.dp).background(TcElevated, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Chat, null, tint = TcTextSecondary, modifier = Modifier.size(18.dp))
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Progress steps
            tripStates.dropLast(1).forEachIndexed { i, step ->
                Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(28.dp).background(if (i <= stateIdx) TcGold else TcCard, CircleShape).border(1.dp, if (i <= stateIdx) TcGold else TcBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (i < stateIdx) Icon(Icons.Default.Check, null, tint = TcBlack, modifier = Modifier.size(14.dp))
                        else Text((i + 1).toString(), color = if (i == stateIdx) TcBlack else TcTextMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(step, color = if (i <= stateIdx) TcTextPrimary else TcTextMuted, fontSize = 14.sp, fontWeight = if (i == stateIdx) FontWeight.Bold else FontWeight.Normal)
                }
            }

            Spacer(Modifier.height(28.dp))
            if (stateIdx < 2) TeddyOutlineButton("Cancel Ride", onClick = onCancel)
        }
    }
}
