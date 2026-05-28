package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryButton
import com.example.ui.components.TeddyTopAppBar
import kotlinx.coroutines.delay

import com.example.ui.components.SupportChatModal
import androidx.compose.material.icons.automirrored.filled.Chat

@Composable
fun RideTrackingScreen(
    userType: String, // "passenger" or "driver"
    onRideComplete: () -> Unit,
    onCancel: () -> Unit
) {
    var status by remember { mutableStateOf("Driver is on the way...") }
    var progress by remember { mutableStateOf(0f) }
    var showSupportChat by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if(userType == "passenger") {
            delay(2000)
            status = "Driver arrived. Boarding..."
            delay(2000)
            status = "Trip in progress..."
            progress = 0.5f
        } else {
            status = "Navigating to passenger..."
            delay(2000)
            status = "Passenger on board. En route..."
            progress = 0.5f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (showSupportChat) {
            SupportChatModal(onDismiss = { showSupportChat = false })
        }
        
        TeddyTopAppBar(
            title = "Trip Status", 
            onBack = onCancel,
            actions = {
                IconButton(onClick = { showSupportChat = true }) {
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Support Chat", tint = MaterialTheme.colorScheme.primary)
                }
            }
        )

        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF222222)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, progress = progress)
                Spacer(modifier = Modifier.height(16.dp))
                Text(status, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    if (userType == "passenger") "Driver: John Doe (KDG 123A)" 
                    else "Passenger: VIP Client",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "Complete Trip", onClick = onRideComplete)
            }
        }
    }
}

@Composable
fun PaymentScreen(
    userType: String,
    onPaymentComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Secure Payment", color = MaterialTheme.colorScheme.primary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Trip Fare", color = Color.Gray)
                Text("KES 1,250", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(24.dp))
                if (userType == "passenger") {
                    Text("Paying via M-Pesa", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Your payment is encrypted and secure.", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Pay Now", onClick = onPaymentComplete)
                } else {
                    Text("Awaiting passenger payment via M-Pesa...", color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Confirm Receipt", onClick = onPaymentComplete)
                }
            }
        }
    }
}

@Composable
fun RatingScreen(
    userType: String,
    onRatingSubmit: () -> Unit
) {
    var rating by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Rate your ${if (userType == "passenger") "driver" else "passenger"}", 
            color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        Row {
            for (i in 1..5) {
                IconButton(onClick = { rating = i }) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Leave a comment (Optional)") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.DarkGray
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(text = "Submit to ensure accountability", onClick = onRatingSubmit)
    }
}
