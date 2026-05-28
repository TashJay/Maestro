package com.example.ui.driver

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.TeddyButton
import com.example.ui.theme.*
import kotlinx.coroutines.delay

private val tripPhases = listOf("Navigate to Pickup", "Arrived at Pickup", "Trip in Progress", "Trip Complete")

@Composable
fun DriverActiveTripScreen(onTripComplete: () -> Unit) {
    var phase by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Status bar
        Box(modifier = Modifier.fillMaxWidth().background(TcGoldFaint).padding(14.dp), contentAlignment = Alignment.Center) {
            Text(tripPhases[phase], color = TcGold, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
        }

        // Map placeholder
        Box(modifier = Modifier.fillMaxWidth().height(260.dp).background(TcSurface), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(when (phase) { 0 -> "🧭"; 1 -> "🚗"; 2 -> "🛣️"; else -> "✅" }, fontSize = 52.sp)
                Text(when (phase) { 0 -> "Navigate to passenger"; 1 -> "Passenger approaching"; 2 -> "On the way to destination"; else -> "Trip completed!" }, color = TcTextMuted, fontSize = 13.sp)
            }
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomEnd) {
                Box(modifier = Modifier.size(42.dp).background(TcBlack, CircleShape).border(1.dp, TcBorder, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Navigation, null, tint = TcGold, modifier = Modifier.size(18.dp))
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
            // Passenger card
            Box(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).background(TcElevated, CircleShape).border(1.dp, TcGold.copy(0.3f), CircleShape), contentAlignment = Alignment.Center) {
                        Text("AK", color = TcGold, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Alex Kim", color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Row { Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(13.dp)); Text(" 4.8 passenger", color = TcTextMuted, fontSize = 12.sp) }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.size(36.dp).background(TcElevated, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Phone, null, tint = TcTextSecondary, modifier = Modifier.size(16.dp))
                        }
                        Box(modifier = Modifier.size(36.dp).background(TcElevated, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Chat, null, tint = TcTextSecondary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Trip info
            Box(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(18.dp)) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("PICKUP", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text("Ngong Road", color = TcTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Icon(Icons.Default.ArrowForward, null, tint = TcTextMuted)
                        Column(horizontalAlignment = Alignment.End) {
                            Text("DROPOFF", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text("Sarit Centre", color = TcTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = TcBorder.copy(0.5f))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Trip Fare", color = TcTextMuted, fontSize = 13.sp)
                        Text("KSh 540", color = TcGold, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            when (phase) {
                0 -> TeddyButton("Arrived at Pickup") { phase = 1 }
                1 -> TeddyButton("Start Trip") { phase = 2 }
                2 -> TeddyButton("Complete Trip") { phase = 3; onTripComplete() }
                else -> {}
            }

            if (phase < 2) {
                Spacer(Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth().background(TcError.copy(0.08f), RoundedCornerShape(12.dp)).border(1.dp, TcError.copy(0.2f), RoundedCornerShape(12.dp)).clickable {}.padding(14.dp), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, null, tint = TcError, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Report emergency (SOS)", color = TcError, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}
