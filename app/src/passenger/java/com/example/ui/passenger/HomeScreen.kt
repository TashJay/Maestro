package com.example.ui.passenger

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.TeddyLoader
import com.example.ui.theme.*

private data class QuickDest(val icon: String, val name: String)
private val quickDests = listOf(QuickDest("🏠","Home"), QuickDest("💼","Office"), QuickDest("🛒","Mall"), QuickDest("✈️","Airport"), QuickDest("🏨","Hotel"))

private data class RecentTrip(val from: String, val to: String, val fare: String, val date: String)
private val recentTrips = listOf(
    RecentTrip("Westlands", "CBD", "KSh 420", "Today, 9:41 AM"),
    RecentTrip("Karen", "JKIA", "KSh 1,200", "Yesterday"),
    RecentTrip("Kilimani", "Sarit Centre", "KSh 380", "Mon, 14 Jan"),
)

@Composable
fun PassengerHomeScreen(onBookRide: () -> Unit, onOpenHistory: () -> Unit, onOpenProfile: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Good morning,", color = TcTextMuted, fontSize = 13.sp)
                Text("Alex 👋", color = TcTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
            Box(
                modifier = Modifier.size(44.dp).background(TcCard, CircleShape).border(1.dp, TcBorder, CircleShape).clickable(onClick = onOpenProfile),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = TcTextSecondary, modifier = Modifier.size(22.dp))
            }
        }

        // Map placeholder
        Box(
            modifier = Modifier.fillMaxWidth().height(220.dp).background(TcSurface),
            contentAlignment = Alignment.Center
        ) {
            // TODO: Replace with GoogleMap composable once Maps API key is configured
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🗺️", fontSize = 48.sp)
                Spacer(Modifier.height(8.dp))
                Text("Add Maps API key to enable live map", color = TcTextMuted, fontSize = 12.sp)
            }
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier.size(42.dp).background(TcBlack, CircleShape).border(1.dp, TcBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MyLocation, null, tint = TcGold, modifier = Modifier.size(18.dp))
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(20.dp))

            // Where to card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TcCard, RoundedCornerShape(16.dp))
                    .border(1.dp, TcBorder, RoundedCornerShape(16.dp))
                    .clickable(onClick = onBookRide)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, null, tint = TcGold, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Where to?", color = TcTextMuted, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("QUICK DESTINATIONS", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(quickDests) { dest ->
                    Column(
                        modifier = Modifier
                            .background(TcCard, RoundedCornerShape(14.dp))
                            .border(1.dp, TcBorder, RoundedCornerShape(14.dp))
                            .clickable(onClick = onBookRide)
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(dest.icon, fontSize = 22.sp)
                        Spacer(Modifier.height(6.dp))
                        Text(dest.name, color = TcTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("RECENT TRIPS", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                Text("See all", color = TcGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onOpenHistory))
            }
            Spacer(Modifier.height(12.dp))

            recentTrips.forEach { trip ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(40.dp).background(TcCard, RoundedCornerShape(10.dp)).border(1.dp, TcBorder, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.DirectionsCar, null, tint = TcTextMuted, modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${trip.from} → ${trip.to}", color = TcTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text(trip.date, color = TcTextMuted, fontSize = 12.sp)
                    }
                    Text(trip.fare, color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Divider(color = TcBorder.copy(0.5f), thickness = 0.5.dp)
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
