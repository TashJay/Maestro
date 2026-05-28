package com.example.ui.components

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ui.theme.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

// ─── Logo Placeholder ────────────────────────────────────────────────────────

@Composable
fun LogoPlaceholder(modifier: Modifier = Modifier, size: Dp = 96.dp) {
    Box(
        modifier = modifier
            .size(size)
            .background(TcGoldFaint, RoundedCornerShape(size * 0.22f))
            .border(1.5.dp, TcGold.copy(alpha = 0.35f), RoundedCornerShape(size * 0.22f)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Add, null, tint = TcGold.copy(0.5f), modifier = Modifier.size(size * 0.32f))
            Spacer(Modifier.height(2.dp))
            Text("LOGO", color = TcGold.copy(0.45f), fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }
    }
}

// ─── Loader ──────────────────────────────────────────────────────────────────

@Composable
fun TeddyLoader(modifier: Modifier = Modifier, size: Dp = 36.dp) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = TcGold,
        strokeWidth = 2.dp,
        trackColor = TcGold.copy(alpha = 0.12f)
    )
}

// ─── Buttons ─────────────────────────────────────────────────────────────────

@Composable
fun TeddyButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, loading: Boolean = false) {
    Button(
        onClick = onClick, enabled = enabled && !loading,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TcGold, contentColor = TcBlack,
            disabledContainerColor = TcGold.copy(0.3f), disabledContentColor = TcBlack.copy(0.4f)
        )
    ) {
        if (loading) TeddyLoader(size = 20.dp) else Text(text, fontWeight = FontWeight.Bold, fontSize = 15.sp, letterSpacing = 0.3.sp)
    }
}

@Composable
fun TeddyOutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, TcBorder),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TcTextPrimary)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
    }
}

@Composable
fun GoogleSignInButton(onResult: (String?, String?) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(ApiException::class.java)
                onResult(account?.email, account?.displayName)
            } catch (_: ApiException) { onResult(null, null) }
        } else onResult(null, null)
    }
    OutlinedButton(
        onClick = {
            // TODO: Replace "YOUR_WEB_CLIENT_ID" with your Firebase OAuth 2.0 Web Client ID
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            launcher.launch(GoogleSignIn.getClient(context, gso).signInIntent)
        },
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, TcBorder),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TcTextPrimary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            Spacer(Modifier.width(10.dp))
            Text("Continue with Google", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
    }
}

// ─── TextField ───────────────────────────────────────────────────────────────

@Composable
fun TeddyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    singleLine: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(TcCard, RoundedCornerShape(14.dp))
            .border(1.dp, TcBorder, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(leadingIcon, null, tint = TcTextMuted, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(12.dp))
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = TcTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Normal),
                cursorBrush = SolidColor(TcGold),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
                visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = singleLine,
                decorationBox = { inner ->
                    if (value.isEmpty()) Text(placeholder, color = TcTextMuted, fontSize = 15.sp)
                    inner()
                }
            )
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }, modifier = Modifier.size(20.dp)) {
                    Icon(if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null, tint = TcTextMuted, modifier = Modifier.size(18.dp))
                }
            } else trailingIcon?.invoke()
        }
    }
}

// ─── OTP Input ───────────────────────────────────────────────────────────────

@Composable
fun OtpInputRow(otp: String, onOtpChange: (String) -> Unit, length: Int = 6) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(length) { i ->
            val char = otp.getOrNull(i)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(0.85f)
                    .background(TcCard, RoundedCornerShape(12.dp))
                    .border(
                        1.5.dp,
                        if (i == otp.length) TcGold else if (char != null) TcGold.copy(0.4f) else TcBorder,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(char?.toString() ?: "", color = TcTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
    Spacer(Modifier.height(4.dp))
    // Hidden real input below — controlled by parent via onOtpChange
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeddyTopBar(title: String, onBack: (() -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        title = { Text(title, color = TcTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        navigationIcon = {
            if (onBack != null) IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary)
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = TcBlack)
    )
}

// ─── Divider with label ───────────────────────────────────────────────────────

@Composable
fun LabeledDivider(label: String = "or") {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier.weight(1f), color = TcBorder)
        Text(label, color = TcTextMuted, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 12.dp))
        Divider(modifier = Modifier.weight(1f), color = TcBorder)
    }
}

// ─── Section label ────────────────────────────────────────────────────────────

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(text, color = TcTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, modifier = modifier)
}

// ─── Stat card ────────────────────────────────────────────────────────────────

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(TcCard, RoundedCornerShape(14.dp))
            .border(1.dp, TcBorder, RoundedCornerShape(14.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = TcGold, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp)
        Spacer(Modifier.height(2.dp))
        Text(label, color = TcTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.sp)
    }
}

// ─── Legacy compatibility ─────────────────────────────────────────────────────

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) = TeddyButton(text, onClick, modifier)

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) = TeddyOutlineButton(text, onClick, modifier)

@Composable
fun TeddyTextField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, isPassword: Boolean = false, leadingIcon: (@Composable () -> Unit)? = null) {
    TeddyTextField(value = value, onValueChange = onValueChange, placeholder = label, modifier = modifier, isPassword = isPassword)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeddyTopAppBar(title: String, onBack: (() -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}) = TeddyTopBar(title, onBack, actions)
