package com.example.ui.driver

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun DriverHomeScreen(onAcceptRide: () -> Unit, onOpenEarnings: () -> Unit, onOpenHistory: () -> Unit, onOpenProfile: () -> Unit) {
    var isOnline by remember { mutableStateOf(false) }
    var showRideRequest by remember { mutableStateOf(false) }
    var rideTimer by remember { mutableIntStateOf(30) }

    LaunchedEffect(isOnline) {
        if (isOnline) {
            delay(4000)
            showRideRequest = true
            rideTimer = 30
            while (rideTimer > 0 && showRideRequest) { delay(1000L); rideTimer-- }
            if (rideTimer == 0) showRideRequest = false
        } else {
            showRideRequest = false
        }
    }

    val pulseAnim = rememberInfiniteTransition(label = "pulse")
    val pulse by pulseAnim.animateFloat(0.97f, 1.03f, infiniteRepeatable(tween(1200), RepeatMode.Reverse), label = "p")

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Good morning,", color = TcTextMuted, fontSize = 13.sp)
                Text("James 👋", color = TcTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
            Box(modifier = Modifier.size(44.dp).background(TcCard, CircleShape).border(1.dp, TcBorder, CircleShape).clickable(onClick = onOpenProfile), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, tint = TcTextSecondary, modifier = Modifier.size(22.dp))
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {

            // Online/Offline toggle
            Box(
                modifier = Modifier.fillMaxWidth().scale(if (isOnline) pulse else 1f)
                    .background(if (isOnline) TcGoldFaint else TcCard, RoundedCornerShape(20.dp))
                    .border(2.dp, if (isOnline) TcGold else TcBorder, RoundedCornerShape(20.dp))
                    .clickable { isOnline = !isOnline; if (!isOnline) showRideRequest = false }
                    .padding(vertical = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(72.dp).background(if (isOnline) TcGold else TcElevated, CircleShape).border(3.dp, if (isOnline) TcGoldLight else TcBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Power, null, tint = if (isOnline) TcBlack else TcTextMuted, modifier = Modifier.size(32.dp))
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(if (isOnline) "YOU ARE ONLINE" else "YOU ARE OFFLINE", color = if (isOnline) TcGold else TcTextMuted, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                    Text(if (isOnline) "Receiving ride requests" else "Tap to go online", color = TcTextMuted, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Earnings row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("KSh 3,280", "TODAY'S EARNINGS", modifier = Modifier.weight(1f))
                StatCard("8", "TRIPS TODAY", modifier = Modifier.weight(1f))
                StatCard("4.92", "RATING", modifier = Modifier.weight(1f))
            }

            // Incoming ride request
            if (showRideRequest) {
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(20.dp)).border(2.dp, TcGold, RoundedCornerShape(20.dp)).padding(20.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("🔔  New Ride Request", color = TcGold, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                            Box(
                                modifier = Modifier.size(38.dp).background(if (rideTimer > 10) TcGoldFaint else TcError.copy(0.15f), CircleShape).border(1.dp, if (rideTimer > 10) TcGold else TcError, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${rideTimer}s", color = if (rideTimer > 10) TcGold else TcError, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Spacer(Modifier.height(14.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("PICKUP", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                                Text("Ngong Road", color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Text("0.8 km away", color = TcTextMuted, fontSize = 12.sp)
                            }
                            Icon(Icons.Default.ArrowForward, null, tint = TcTextMuted, modifier = Modifier.align(Alignment.CenterVertically))
                            Column(horizontalAlignment = Alignment.End) {
                                Text("DROPOFF", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                                Text("Sarit Centre", color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Text("6.2 km total", color = TcTextMuted, fontSize = 12.sp)
                            }
                        }

                        Spacer(Modifier.height(6.dp))
                        Divider(color = TcBorder.copy(0.5f))
                        Spacer(Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("KSh 540", color = TcGold, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier.size(48.dp).background(TcError.copy(0.12f), CircleShape).border(1.dp, TcError.copy(0.4f), CircleShape).clickable { showRideRequest = false },
                                    contentAlignment = Alignment.Center
                                ) { Icon(Icons.Default.Close, null, tint = TcError, modifier = Modifier.size(22.dp)) }
                                Box(
                                    modifier = Modifier.size(48.dp).background(TcSuccess.copy(0.12f), CircleShape).border(1.dp, TcSuccess.copy(0.4f), CircleShape).clickable { showRideRequest = false; onAcceptRide() },
                                    contentAlignment = Alignment.Center
                                ) { Icon(Icons.Default.Check, null, tint = TcSuccess, modifier = Modifier.size(22.dp)) }
                            }
                        }
                    }
                }
            }

            if (!isOnline) {
                Spacer(Modifier.height(20.dp))
                Box(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(14.dp)).border(1.dp, TcBorder, RoundedCornerShape(14.dp)).padding(20.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("🗺️", fontSize = 36.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Go online to start receiving ride requests", color = TcTextMuted, fontSize = 14.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            SectionLabel("QUICK ACCESS")
            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(Icons.Default.BarChart to "Earnings", Icons.Default.History to "Trips").forEachIndexed { i, (icon, label) ->
                    Box(
                        modifier = Modifier.weight(1f).background(TcCard, RoundedCornerShape(14.dp)).border(1.dp, TcBorder, RoundedCornerShape(14.dp))
                            .clickable { if (i == 0) onOpenEarnings() else onOpenHistory() }.padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(icon, null, tint = TcGold, modifier = Modifier.size(24.dp))
                            Spacer(Modifier.height(6.dp))
                            Text(label, color = TcTextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}
