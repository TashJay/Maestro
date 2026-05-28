package com.example.ui.passenger

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*

// ─── Payment ──────────────────────────────────────────────────────────────────

@Composable
fun PassengerPaymentScreen(onPaymentDone: () -> Unit) {
    var selectedMethod by remember { mutableIntStateOf(0) }
    var loading by remember { mutableStateOf(false) }

    val methods = listOf("📱 M-Pesa", "💳 Credit Card", "💵 Cash")
    val fareItems = listOf("Base fare" to "KSh 80", "Distance (6.4 km)" to "KSh 352", "Service fee" to "KSh 40")
    val total = "KSh 472"

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
        Spacer(Modifier.height(32.dp))
        Text("Trip Summary", color = TcTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("Westlands → CBD · 18 min", color = TcTextMuted, fontSize = 14.sp)
        Spacer(Modifier.height(28.dp))

        // Fare breakdown
        Box(modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(16.dp)).border(1.dp, TcBorder, RoundedCornerShape(16.dp)).padding(20.dp)) {
            Column {
                SectionLabel("FARE BREAKDOWN")
                Spacer(Modifier.height(16.dp))
                fareItems.forEach { (label, value) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(label, color = TcTextSecondary, fontSize = 14.sp)
                        Text(value, color = TcTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = TcBorder)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", color = TcTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(total, color = TcGold, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        SectionLabel("PAYMENT METHOD")
        Spacer(Modifier.height(14.dp))

        methods.forEachIndexed { i, method ->
            val selected = selectedMethod == i
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                    .background(if (selected) TcGoldFaint else TcCard, RoundedCornerShape(14.dp))
                    .border(1.5.dp, if (selected) TcGold else TcBorder, RoundedCornerShape(14.dp))
                    .clickable { selectedMethod = i }.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(method, color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                if (selected) Icon(Icons.Default.CheckCircle, null, tint = TcGold, modifier = Modifier.size(20.dp))
                else Box(modifier = Modifier.size(20.dp).border(1.5.dp, TcBorder, CircleShape))
            }
        }

        if (selectedMethod == 0) {
            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().background(TcElevated, RoundedCornerShape(12.dp)).padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, null, tint = TcTextMuted, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Push notification will be sent to +254 7XX XXX XXX", color = TcTextMuted, fontSize = 13.sp)
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        TeddyButton("Pay $total", loading = loading, onClick = {
            // TODO: Connect to M-Pesa / Stripe API
            loading = true
            onPaymentDone()
        })
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Rating ───────────────────────────────────────────────────────────────────

@Composable
fun PassengerRatingScreen(onSubmit: () -> Unit) {
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val compliments = listOf("Great navigation", "Very punctual", "Friendly", "Car was clean", "Safe driver")
    var selectedCompliments by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(40.dp))
        Text("Rate your trip", color = TcTextPrimary, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("How was your ride with James?", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(32.dp))

        // Driver avatar
        Box(modifier = Modifier.size(72.dp).background(TcCard, CircleShape).border(2.dp, TcGold.copy(0.3f), CircleShape), contentAlignment = Alignment.Center) {
            Text("JO", color = TcGold, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(Modifier.height(10.dp))
        Text("James Otieno", color = TcTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(28.dp))

        // Star rating
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(5) { i ->
                Icon(
                    if (i < rating) Icons.Default.Star else Icons.Default.StarOutline,
                    null, tint = if (i < rating) TcGold else TcBorder,
                    modifier = Modifier.size(44.dp).clickable { rating = i + 1 }
                )
            }
        }

        if (rating > 0) {
            Spacer(Modifier.height(28.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)) {
                compliments.forEach { tag ->
                    val sel = tag in selectedCompliments
                    Text(
                        tag, color = if (sel) TcBlack else TcTextSecondary, fontSize = 12.sp,
                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .background(if (sel) TcGold else TcCard, RoundedCornerShape(20.dp))
                            .border(1.dp, if (sel) TcGold else TcBorder, RoundedCornerShape(20.dp))
                            .clickable { selectedCompliments = if (sel) selectedCompliments - tag else selectedCompliments + tag }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(14.dp)).border(1.dp, TcBorder, RoundedCornerShape(14.dp)).padding(16.dp)
            ) {
                androidx.compose.foundation.text.BasicTextField(
                    value = comment, onValueChange = { comment = it },
                    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 80.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(color = TcTextPrimary, fontSize = 14.sp),
                    cursorBrush = androidx.compose.ui.graphics.SolidColor(TcGold),
                    decorationBox = { inner ->
                        if (comment.isEmpty()) Text("Add a comment (optional)…", color = TcTextMuted, fontSize = 14.sp)
                        inner()
                    }
                )
            }
        }

        Spacer(Modifier.height(32.dp))
        TeddyButton(if (rating == 0) "Skip" else "Submit Rating", enabled = true, onClick = {
            // TODO: Submit rating to backend
            onSubmit()
        })
        Spacer(Modifier.height(40.dp))
    }
}
