package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.GpsFixed

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { 3 })
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.pager.HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (page) {
                    0 -> {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Premium",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "TEDDY CABS",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 4.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Premium Ride Hailing. Experience elegance in every journey.",
                            color = Color.LightGray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    1 -> {
                        Icon(
                            imageVector = Icons.Filled.Payment,
                            contentDescription = "Payment",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Secure M-Pesa",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Seamless in-app payments. Your data stays encrypted for maximum security.",
                            color = Color.LightGray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    2 -> {
                        Icon(
                            imageVector = Icons.Filled.GpsFixed,
                            contentDescription = "Tracking",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Real-Time GPS",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Track your ride instantly. Mutual rating system ensures accountability.",
                            color = Color.LightGray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { iteration ->
                val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.DarkGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(color, androidx.compose.foundation.shape.CircleShape)
                        .size(10.dp)
                )
            }
        }
        
        Column(modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            PrimaryButton(
                text = "Login",
                onClick = onNavigateToLogin
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                text = "Sign Up",
                onClick = onNavigateToSignup
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: (Boolean) -> Unit, // true if driver
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isDriver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TeddyTopAppBar(title = "Login", onBack = onBack)
        
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Welcome back, elegance awaits.", color = Color.LightGray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(32.dp))
            
            TeddyTextField(value = email, onValueChange = { email = it }, label = "Email or Phone")
            Spacer(modifier = Modifier.height(16.dp))
            TeddyTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDriver,
                    onCheckedChange = { isDriver = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.Black
                    )
                )
                Text("I am a driver", color = Color.White)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "Sign In", onClick = { onLoginSuccess(isDriver) })
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SignupScreen(
    onSignupSuccess: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isDriver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TeddyTopAppBar(title = "Create Profile", onBack = onBack)
        
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            TeddyTextField(value = name, onValueChange = { name = it }, label = "Full Name")
            Spacer(modifier = Modifier.height(16.dp))
            TeddyTextField(value = email, onValueChange = { email = it }, label = "Email")
            Spacer(modifier = Modifier.height(16.dp))
            TeddyTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDriver,
                    onCheckedChange = { isDriver = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.Black
                    )
                )
                Text("Register as driver", color = Color.White)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "Sign Up", onClick = { onSignupSuccess(isDriver) })
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
