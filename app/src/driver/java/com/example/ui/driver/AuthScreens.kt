package com.example.ui.driver

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

// ─── Login ────────────────────────────────────────────────────────────────────

@Composable
fun DriverLoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignup: () -> Unit, onGoogleSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp), verticalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(60.dp))
        Text("Driver sign in.", color = TcTextPrimary, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("Ready to earn? Let's go.", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(40.dp))

        TeddyTextField(value = email, onValueChange = { email = it }, placeholder = "Email address", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = password, onValueChange = { password = it }, placeholder = "Password", leadingIcon = Icons.Default.Lock, isPassword = true)
        Spacer(Modifier.height(8.dp))
        Text("Forgot password?", color = TcGold, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { }.align(Alignment.End))
        Spacer(Modifier.height(28.dp))

        TeddyButton("Sign In", loading = loading, onClick = {
            loading = true
            // TODO: Connect to auth backend
            onLoginSuccess()
        })
        Spacer(Modifier.height(24.dp))
        LabeledDivider()
        Spacer(Modifier.height(24.dp))
        GoogleSignInButton(onResult = { email, name -> if (email != null) onGoogleSuccess() })
        Spacer(Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("New driver?  ", color = TcTextMuted, fontSize = 14.sp)
            Text("Create account", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onNavigateToSignup))
        }
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Signup ───────────────────────────────────────────────────────────────────

@Composable
fun DriverSignupScreen(onNavigateToOtp: (String) -> Unit, onNavigateToLogin: () -> Unit, onGoogleSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp), verticalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(60.dp))
        Text("Become a driver.", color = TcTextPrimary, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("Start earning with Teddy Cabs.", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(8.dp))

        // Progress indicator
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.height(3.dp).weight(1f).background(TcGold, androidx.compose.foundation.shape.CircleShape))
            Box(modifier = Modifier.height(3.dp).weight(1f).background(TcBorder, androidx.compose.foundation.shape.CircleShape))
            Box(modifier = Modifier.height(3.dp).weight(1f).background(TcBorder, androidx.compose.foundation.shape.CircleShape))
            Box(modifier = Modifier.height(3.dp).weight(1f).background(TcBorder, androidx.compose.foundation.shape.CircleShape))
        }
        Text("Step 1 of 4 — Account", color = TcTextMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp, bottom = 24.dp))

        TeddyTextField(value = name, onValueChange = { name = it }, placeholder = "Full name", leadingIcon = Icons.Default.Person)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = email, onValueChange = { email = it }, placeholder = "Email address", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = phone, onValueChange = { phone = it }, placeholder = "Phone number", leadingIcon = Icons.Default.Phone, keyboardType = KeyboardType.Phone)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = password, onValueChange = { password = it }, placeholder = "Create password", leadingIcon = Icons.Default.Lock, isPassword = true)
        Spacer(Modifier.height(28.dp))

        TeddyButton("Continue") { if (phone.isNotEmpty()) onNavigateToOtp(phone.trim()) }
        Spacer(Modifier.height(24.dp))
        LabeledDivider()
        Spacer(Modifier.height(24.dp))
        GoogleSignInButton(onResult = { e, n -> if (e != null) onGoogleSuccess() })
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Already a driver?  ", color = TcTextMuted, fontSize = 14.sp)
            Text("Sign in", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onNavigateToLogin))
        }
        Spacer(Modifier.height(40.dp))
    }
}

// ─── OTP ─────────────────────────────────────────────────────────────────────

@Composable
fun DriverOtpScreen(phone: String, onVerified: () -> Unit, onBack: () -> Unit) {
    var otp by remember { mutableStateOf("") }
    var timer by remember { mutableIntStateOf(60) }
    var canResend by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { while (timer > 0) { delay(1000L); timer-- }; canResend = true }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp)) {
        Spacer(Modifier.height(28.dp))
        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
        Spacer(Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(4) { i -> Box(modifier = Modifier.height(3.dp).weight(1f).background(if (i < 2) TcGold else TcBorder, androidx.compose.foundation.shape.CircleShape)) }
        }
        Text("Step 2 of 4 — Verify", color = TcTextMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp, bottom = 24.dp))

        Text("Verify your number.", color = TcTextPrimary, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(8.dp))
        Text("Enter the 6-digit code sent to\n$phone", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(48.dp))

        OtpInputRow(otp = otp, onOtpChange = {})
        Spacer(Modifier.height(8.dp))
        androidx.compose.foundation.text.BasicTextField(
            value = otp, onValueChange = { if (it.length <= 6 && it.all(Char::isDigit)) otp = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth().height(1.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.Transparent)
        )

        Spacer(Modifier.height(40.dp))
        TeddyButton("Verify & Continue", enabled = otp.length == 6, onClick = { onVerified() })
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (canResend) {
                Text("Didn't get code?  ", color = TcTextMuted, fontSize = 14.sp)
                Text("Resend", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { otp = ""; timer = 60; canResend = false })
            } else Text("Resend code in ${timer}s", color = TcTextMuted, fontSize = 14.sp)
        }
    }
}
