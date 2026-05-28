package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldGradient
import com.example.ui.theme.CreamText
import com.example.ui.theme.GoldPrimary

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(12.dp, RoundedCornerShape(8.dp), spotColor = GoldPrimary, ambientColor = GoldPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = GoldGradient, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text.uppercase(), fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp, color = Color(0xFF111111))
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold, letterSpacing = 2.sp)
    }
}

@Composable
fun TeddyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    androidx.compose.material3.TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray, fontWeight = FontWeight.Medium) },
        leadingIcon = leadingIcon,
        modifier = modifier.fillMaxWidth(),
        colors = androidx.compose.material3.TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF252527),
            unfocusedContainerColor = Color(0xFF252527),
            focusedIndicatorColor = GoldPrimary,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = GoldPrimary,
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(color = CreamText, fontWeight = FontWeight.Medium, fontSize = 16.sp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeddyTopAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title, 
                color = GoldPrimary,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 1.sp
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun SupportChatModal(
    onDismiss: () -> Unit
) {
    var issue by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(text = "Support Chat", color = MaterialTheme.colorScheme.primary) 
        },
        text = { 
            androidx.compose.foundation.layout.Column {
                Text("How can we help you with your trip?", color = Color.White)
                androidx.compose.foundation.layout.Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = issue,
                    onValueChange = { issue = it },
                    label = { Text("Describe your issue...") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.DarkGray,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = Color.Gray
                    )
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { 
                Text("Send Message", color = Color.Black) 
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) { 
                Text("Cancel", color = Color.LightGray) 
            }
        }
    )
}

