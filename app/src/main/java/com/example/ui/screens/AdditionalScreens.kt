package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveEta
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryButton
import com.example.ui.components.TeddyTextField
import com.example.ui.components.TeddyTopAppBar

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var phone by remember { mutableStateOf("+254 712 345 678") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TeddyTopAppBar(title = "Edit Profile", onBack = onBack)
        
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text("Update your personal details below.", color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))
            
            TeddyTextField(value = name, onValueChange = { name = it }, label = "Full Name")
            Spacer(modifier = Modifier.height(16.dp))
            TeddyTextField(value = email, onValueChange = { email = it }, label = "Email")
            Spacer(modifier = Modifier.height(16.dp))
            TeddyTextField(value = phone, onValueChange = { phone = it }, label = "Phone Number")
            
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "Save Changes", onClick = onBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TripHistoryScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TeddyTopAppBar(title = "Trip History", onBack = onBack)
        
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DriveEta,
                            contentDescription = "Car",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("May ${24 - index}, 2026", color = Color.Gray, fontSize = 12.sp)
                            Text(if (index % 2 == 0) "Airport Dropoff" else "City Center Ride", color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(if (index % 2 == 0) "KES 1,250" else "KES 400", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
