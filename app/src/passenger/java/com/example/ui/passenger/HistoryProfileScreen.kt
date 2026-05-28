package com.example.ui.passenger

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*

// ─── History ─────────────────────────────────────────────────────────────────

private data class TripRecord(val from: String, val to: String, val fare: String, val date: String, val rating: Int, val driver: String)
private val tripHistory = listOf(
    TripRecord("Westlands", "CBD", "KSh 420", "Today · 9:41 AM", 5, "James O."),
    TripRecord("Karen", "JKIA", "KSh 1,200", "Yesterday · 6:15 AM", 4, "Peter K."),
    TripRecord("Kilimani", "Sarit Centre", "KSh 380", "Mon 14 Jan · 2:00 PM", 5, "Grace W."),
    TripRecord("Ngong Rd", "Upperhill", "KSh 290", "Sun 13 Jan · 10:30 AM", 4, "Samuel K."),
    TripRecord("CBD", "Lavington", "KSh 510", "Fri 11 Jan · 7:45 PM", 5, "Monica A."),
    TripRecord("Gigiri", "Westlands", "KSh 340", "Thu 10 Jan · 8:10 AM", 3, "Brian M."),
)

@Composable
fun PassengerHistoryScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Text("Trip History", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            items(tripHistory) { trip ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                        .background(TcCard, RoundedCornerShape(16.dp))
                        .border(1.dp, TcBorder, RoundedCornerShape(16.dp))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("${trip.from} → ${trip.to}", color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(trip.fare, color = TcGold, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, null, tint = TcTextMuted, modifier = Modifier.size(13.dp))
                            Text(" ${trip.driver}  ·  ${trip.date}", color = TcTextMuted, fontSize = 12.sp)
                            Spacer(Modifier.weight(1f))
                            Row {
                                repeat(trip.rating) { Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(13.dp)) }
                            }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

// ─── Profile ─────────────────────────────────────────────────────────────────

@Composable
fun PassengerProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    var name by remember { mutableStateOf("Alex Kim") }
    var email by remember { mutableStateOf("alex.kim@email.com") }
    var phone by remember { mutableStateOf("+254 712 345 678") }
    var editing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp, top = 20.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Text("Profile", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Text(if (editing) "Done" else "Edit", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { editing = !editing })
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // Avatar
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box {
                    Box(modifier = Modifier.size(88.dp).background(TcCard, CircleShape).border(2.dp, TcGold.copy(0.3f), CircleShape), contentAlignment = Alignment.Center) {
                        Text(name.take(2).uppercase(), color = TcGold, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Box(
                        modifier = Modifier.size(28.dp).align(Alignment.BottomEnd).background(TcGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CameraAlt, null, tint = TcBlack, modifier = Modifier.size(14.dp))
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(name, color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, null, tint = TcGold, modifier = Modifier.size(14.dp))
                Text(" 4.8 passenger rating", color = TcTextMuted, fontSize = 13.sp)
            }
            Spacer(Modifier.height(32.dp))

            SectionLabel("PERSONAL DETAILS")
            Spacer(Modifier.height(12.dp))

            if (editing) {
                TeddyTextField(value = name, onValueChange = { name = it }, placeholder = "Full name", leadingIcon = Icons.Default.Person)
                Spacer(Modifier.height(10.dp))
                TeddyTextField(value = email, onValueChange = { email = it }, placeholder = "Email", leadingIcon = Icons.Default.Email)
                Spacer(Modifier.height(10.dp))
                TeddyTextField(value = phone, onValueChange = { phone = it }, placeholder = "Phone", leadingIcon = Icons.Default.Phone)
            } else {
                listOf(Icons.Default.Person to name, Icons.Default.Email to email, Icons.Default.Phone to phone).forEach { (icon, value) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(icon, null, tint = TcTextMuted, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(14.dp))
                        Text(value, color = TcTextPrimary, fontSize = 15.sp)
                    }
                    Divider(color = TcBorder.copy(0.5f), thickness = 0.5.dp)
                }
            }

            Spacer(Modifier.height(28.dp))
            SectionLabel("PREFERENCES")
            Spacer(Modifier.height(12.dp))

            listOf("Notifications", "Saved Locations", "Payment Methods", "Help & Support").forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {}.padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
