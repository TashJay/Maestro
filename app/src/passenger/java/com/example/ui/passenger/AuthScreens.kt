package com.example.ui.passenger

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun PassengerLoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignup: () -> Unit, onGoogleSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(60.dp))

        Text("Welcome back.", color = TcTextPrimary, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("Sign in to continue your journey.", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(40.dp))

        TeddyTextField(value = email, onValueChange = { email = it }, placeholder = "Email address", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = password, onValueChange = { password = it }, placeholder = "Password", leadingIcon = Icons.Default.Lock, isPassword = true)
        Spacer(Modifier.height(8.dp))
        Text("Forgot password?", color = TcGold, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { }.align(Alignment.End))
        Spacer(Modifier.height(28.dp))

        TeddyButton("Sign In", onClick = {
            loading = true
            // TODO: Connect to your auth backend
            onLoginSuccess()
        }, loading = loading)

        Spacer(Modifier.height(24.dp))
        LabeledDivider()
        Spacer(Modifier.height(24.dp))

        GoogleSignInButton(onResult = { email, name ->
            if (email != null) onGoogleSuccess()
        })

        Spacer(Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Don't have an account?  ", color = TcTextMuted, fontSize = 14.sp)
            Text("Sign up", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onNavigateToSignup))
        }
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Signup ───────────────────────────────────────────────────────────────────

@Composable
fun PassengerSignupScreen(onNavigateToOtp: (String) -> Unit, onNavigateToLogin: () -> Unit, onGoogleSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(60.dp))

        Text("Create account.", color = TcTextPrimary, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(6.dp))
        Text("Rides at your fingertips, starting now.", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(40.dp))

        TeddyTextField(value = name, onValueChange = { name = it }, placeholder = "Full name", leadingIcon = Icons.Default.Person)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = email, onValueChange = { email = it }, placeholder = "Email address", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = phone, onValueChange = { phone = it }, placeholder = "Phone number", leadingIcon = Icons.Default.Phone, keyboardType = KeyboardType.Phone)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = password, onValueChange = { password = it }, placeholder = "Create password", leadingIcon = Icons.Default.Lock, isPassword = true)
        Spacer(Modifier.height(28.dp))

        TeddyButton("Create Account") {
            if (phone.isNotEmpty()) onNavigateToOtp(phone.trim())
        }

        Spacer(Modifier.height(24.dp))
        LabeledDivider()
        Spacer(Modifier.height(24.dp))

        GoogleSignInButton(onResult = { email, name -> if (email != null) onGoogleSuccess() })

        Spacer(Modifier.height(20.dp))
        Text(
            "By signing up you agree to our Terms of Service and Privacy Policy.",
            color = TcTextMuted, fontSize = 12.sp, textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Already have an account?  ", color = TcTextMuted, fontSize = 14.sp)
            Text("Sign in", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onNavigateToLogin))
        }
        Spacer(Modifier.height(40.dp))
    }
}

// ─── OTP ─────────────────────────────────────────────────────────────────────

@Composable
fun PassengerOtpScreen(phone: String, onVerified: () -> Unit, onBack: () -> Unit) {
    var otp by remember { mutableStateOf("") }
    var timer by remember { mutableIntStateOf(60) }
    var canResend by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timer > 0) { delay(1000L); timer-- }
        canResend = true
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp)) {
        Spacer(Modifier.height(28.dp))
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary)
        }
        Spacer(Modifier.height(32.dp))

        Text("Verify your number.", color = TcTextPrimary, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(8.dp))
        Text("Enter the 6-digit code sent to\n$phone", color = TcTextMuted, fontSize = 15.sp)
        Spacer(Modifier.height(48.dp))

        // OTP boxes (visual display)
        OtpInputRow(otp = otp, onOtpChange = {})
        Spacer(Modifier.height(8.dp))

        // Hidden real input for keyboard
        androidx.compose.foundation.text.BasicTextField(
            value = otp,
            onValueChange = { if (it.length <= 6 && it.all(Char::isDigit)) otp = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth().height(1.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.Transparent)
        )

        Spacer(Modifier.height(40.dp))
        TeddyButton("Verify Code", enabled = otp.length == 6, onClick = {
            // TODO: Validate OTP with backend
            onVerified()
        })

        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (canResend) {
                Text("Resend code  ", color = TcTextMuted, fontSize = 14.sp)
                Text("Resend", color = TcGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { otp = ""; timer = 60; canResend = false })
            } else {
                Text("Resend code in ${timer}s", color = TcTextMuted, fontSize = 14.sp)
            }
        }
    }
}
