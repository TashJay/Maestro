package com.example.ui.driver

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*

// ─── Earnings ─────────────────────────────────────────────────────────────────

@Composable
fun DriverEarningsScreen(onBack: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Today", "This Week", "This Month")
    val earnings = listOf("KSh 3,280", "KSh 22,140", "KSh 62,400")
    val trips = listOf("8", "54", "201")
    val bars = listOf(
        listOf(0.4f, 0.65f, 0.5f, 0.8f, 0.55f, 1f, 0.3f),
        listOf(0.6f, 0.5f, 0.8f, 0.4f, 0.9f, 0.7f, 0.85f),
        listOf(0.45f, 0.7f, 0.6f, 0.9f, 0.5f, 0.8f, 0.65f),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Text("Earnings", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
            // Tab row
            Row(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(12.dp)).border(1.dp, TcBorder, RoundedCornerShape(12.dp)).padding(4.dp)) {
                tabs.forEachIndexed { i, tab ->
                    Box(
                        modifier = Modifier.weight(1f).background(if (selectedTab == i) TcGold else androidx.compose.ui.graphics.Color.Transparent, RoundedCornerShape(10.dp)).clickable { selectedTab = i }.padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(tab, color = if (selectedTab == i) TcBlack else TcTextMuted, fontSize = 13.sp, fontWeight = if (selectedTab == i) FontWeight.Bold else FontWeight.Normal) }
                }
            }

            Spacer(Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth().background(TcGoldFaint, RoundedCornerShape(20.dp)).border(1.dp, TcGold.copy(0.2f), RoundedCornerShape(20.dp)).padding(24.dp)) {
                Column {
                    Text("TOTAL EARNED", color = TcTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text(earnings[selectedTab], color = TcGold, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        Column { Text("TRIPS", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp); Text(trips[selectedTab], color = TcTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                        Column { Text("ACCEPT RATE", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp); Text("94%", color = TcTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                        Column { Text("ONLINE HRS", color = TcTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp); Text("7.2h", color = TcTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                    }
                }
            }

            // Chart
            Spacer(Modifier.height(20.dp))
            SectionLabel("DAILY BREAKDOWN")
            Spacer(Modifier.height(12.dp))
            val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            Box(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(16.dp)) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().height(100.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly) {
                        bars[selectedTab].forEachIndexed { i, h ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.width(28.dp).fillMaxHeight(h)
                                        .background(if (i == 5) TcGold else TcGold.copy(0.3f), RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        dayLabels.forEach { Text(it, color = TcTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium) }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            TeddyButton("Request Payout") { /* TODO: Trigger M-Pesa payout */ }
            Spacer(Modifier.height(40.dp))
        }
    }
}

// ─── History ──────────────────────────────────────────────────────────────────

private data class DriverTrip(val from: String, val to: String, val earning: String, val date: String, val rating: Int)
private val driverHistory = listOf(
    DriverTrip("Ngong Rd", "Sarit Centre", "KSh 540", "Today · 9:10 AM", 5),
    DriverTrip("Westlands", "CBD", "KSh 420", "Today · 8:05 AM", 4),
    DriverTrip("Karen", "JKIA", "KSh 1,200", "Yesterday · 6:00 AM", 5),
    DriverTrip("Kilimani", "Gigiri", "KSh 780", "Mon 14 Jan", 4),
    DriverTrip("Lavington", "Upperhill", "KSh 390", "Sun 13 Jan", 5),
    DriverTrip("CBD", "Buruburu", "KSh 620", "Fri 11 Jan", 3),
)

@Composable
fun DriverHistoryScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Text("Trip History", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            items(driverHistory) { trip ->
                Box(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(18.dp)) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("${trip.from} → ${trip.to}", color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(trip.earning, color = TcGold, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(Modifier.height(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(trip.date, color = TcTextMuted, fontSize = 12.sp)
                            Spacer(Modifier.weight(1f))
                            repeat(trip.rating) { Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(12.dp)) }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

// ─── Profile ──────────────────────────────────────────────────────────────────

@Composable
fun DriverProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    var name by remember { mutableStateOf("James Otieno") }
    var editing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Text("Profile", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Text(if (editing) "Done" else "Edit", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { editing = !editing })
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box {
                    Box(modifier = Modifier.size(88.dp).background(TcCard, CircleShape).border(2.dp, TcGold.copy(0.3f), CircleShape), contentAlignment = Alignment.Center) {
                        Text(name.take(2).uppercase(), color = TcGold, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Box(modifier = Modifier.size(28.dp).align(Alignment.BottomEnd).background(TcGold, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CameraAlt, null, tint = TcBlack, modifier = Modifier.size(14.dp))
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(name, color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(14.dp))
                Text(" 4.92 · 1,240 trips", color = TcTextMuted, fontSize = 13.sp)
            }
            Spacer(Modifier.height(24.dp))

            // Document status
            SectionLabel("DOCUMENT STATUS")
            Spacer(Modifier.height(12.dp))
            listOf("National ID" to true, "Driver's Licence" to true, "Vehicle Registration" to true, "Insurance" to false).forEach { (doc, verified) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (verified) Icons.Default.CheckCircle else Icons.Default.Schedule, null, tint = if (verified) TcSuccess else TcWarning, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(doc, color = TcTextPrimary, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Text(if (verified) "Verified" else "Pending", color = if (verified) TcSuccess else TcWarning, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Divider(color = TcBorder.copy(0.5f), thickness = 0.5.dp)
            }

            Spacer(Modifier.height(24.dp))
            SectionLabel("SETTINGS")
            Spacer(Modifier.height(12.dp))
            listOf("Notification Preferences", "Payout Settings", "Vehicle Details", "Help & Support").forEach { item ->
                Row(modifier = Modifier.fillMaxWidth().clickable {}.padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(item, color = TcTextPrimary, fontSize = 15.sp)
                    Icon(Icons.Default.ChevronRight, null, tint = TcTextMuted, modifier = Modifier.size(18.dp))
                }
                Divider(color = TcBorder.copy(0.5f), thickness = 0.5.dp)
            }

            Spacer(Modifier.height(32.dp))
            TeddyOutlineButton("Sign Out", onClick = onLogout)
            Spacer(Modifier.height(40.dp))
        }
    }
}
