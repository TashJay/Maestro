package com.example.ui.passenger

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*

private data class RideType(val name: String, val emoji: String, val desc: String, val range: String, val eta: String)
private val rideTypes = listOf(
    RideType("Standard", "🚗", "Everyday rides", "KSh 350–500", "~4 min"),
    RideType("Premium", "⭐", "Premium comfort", "KSh 600–900", "~6 min"),
    RideType("XL", "🚐", "Up to 6 people", "KSh 800–1,200", "~8 min"),
)

@Composable
fun PassengerBookRideScreen(onRideRequested: () -> Unit, onBack: () -> Unit) {
    var pickup by remember { mutableStateOf("") }
    var dropoff by remember { mutableStateOf("") }
    var selectedRide by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = TcTextPrimary) }
            Text("Book a Ride", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
            // Route card
            Box(
                modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(androidx.compose.foundation.shape.CircleShape).background(TcGold))
                        Spacer(Modifier.width(14.dp))
                        Box(
                            modifier = Modifier.weight(1f).background(TcElevated, RoundedCornerShape(10.dp)).padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            if (pickup.isEmpty()) Text("Pickup location", color = TcTextMuted, fontSize = 14.sp)
                            else Text(pickup, color = TcTextPrimary, fontSize = 14.sp)
                        }
                    }
                    Box(modifier = Modifier.padding(start = 4.dp, top = 6.dp, bottom = 6.dp).size(width = 2.dp, height = 20.dp).background(TcBorder))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(androidx.compose.foundation.shape.CircleShape).background(TcTextMuted))
                        Spacer(Modifier.width(14.dp))
                        Box(
                            modifier = Modifier.weight(1f).background(TcElevated, RoundedCornerShape(10.dp)).clickable {}.padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            if (dropoff.isEmpty()) Text("Where to?", color = TcTextMuted, fontSize = 14.sp)
                            else Text(dropoff, color = TcTextPrimary, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            TeddyTextField(value = pickup, onValueChange = { pickup = it }, placeholder = "Enter pickup address", leadingIcon = Icons.Default.LocationOn)
            Spacer(Modifier.height(8.dp))
            TeddyTextField(value = dropoff, onValueChange = { dropoff = it }, placeholder = "Enter destination", leadingIcon = Icons.Default.Flag)

            Spacer(Modifier.height(28.dp))
            SectionLabel("CHOOSE RIDE TYPE")
            Spacer(Modifier.height(14.dp))

            rideTypes.forEachIndexed { i, ride ->
                val selected = selectedRide == i
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(if (selected) TcGoldFaint else TcCard, RoundedCornerShape(16.dp))
                        .border(1.5.dp, if (selected) TcGold else TcBorder, RoundedCornerShape(16.dp))
                        .clickable { selectedRide = i }
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(ride.emoji, fontSize = 28.sp)
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(ride.name, color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(ride.desc, color = TcTextMuted, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(ride.range, color = if (selected) TcGold else TcTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(ride.eta, color = TcTextMuted, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            // Fare estimate strip
            Box(
                modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(12.dp)).border(1.dp, TcBorder, RoundedCornerShape(12.dp)).padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("ESTIMATED FARE", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                        Text(rideTypes[selectedRide].range, color = TcGold, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("ETA", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                        Text(rideTypes[selectedRide].eta, color = TcTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            TeddyButton("Request ${rideTypes[selectedRide].name} Ride", loading = loading, onClick = {
                // TODO: Call ride matching API
                loading = true
                onRideRequested()
            })
            Spacer(Modifier.height(40.dp))
        }
    }
}
