package com.example.ui.driver

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.ui.components.*
import com.example.ui.theme.*

// ─── Step 1: Personal Details ─────────────────────────────────────────────────

@Composable
fun RegistrationStep1Screen(onNext: () -> Unit, onBack: () -> Unit) {
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
        Spacer(Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Spacer(Modifier.width(4.dp))
            Column {
                Text("Personal Details", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("Step 3 of 4", color = TcTextMuted, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(4) { i -> Box(modifier = Modifier.height(3.dp).weight(1f).background(if (i < 3) TcGold else TcBorder, androidx.compose.foundation.shape.CircleShape)) }
        }
        Spacer(Modifier.height(28.dp))

        TeddyTextField(value = idNumber, onValueChange = { idNumber = it }, placeholder = "National ID number", leadingIcon = Icons.Default.CreditCard, keyboardType = KeyboardType.Number)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = dob, onValueChange = { dob = it }, placeholder = "Date of birth (DD/MM/YYYY)", leadingIcon = Icons.Default.CalendarToday)
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = address, onValueChange = { address = it }, placeholder = "Home address", leadingIcon = Icons.Default.Home)
        Spacer(Modifier.height(12.dp))

        // Gender selector
        SectionLabel("GENDER")
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("Male", "Female", "Other").forEach { g ->
                val sel = gender == g
                Box(
                    modifier = Modifier.weight(1f).background(if (sel) TcGoldFaint else TcCard, RoundedCornerShape(12.dp)).border(1.5.dp, if (sel) TcGold else TcBorder, RoundedCornerShape(12.dp)).clickable { gender = g }.padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) { Text(g, color = if (sel) TcGold else TcTextSecondary, fontSize = 14.sp, fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal) }
            }
        }

        Spacer(Modifier.height(32.dp))
        TeddyButton("Continue") { onNext() }
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Step 2: Documents ────────────────────────────────────────────────────────

@Composable
fun RegistrationStep2Screen(onNext: () -> Unit, onBack: () -> Unit) {
    var idFront by remember { mutableStateOf<Uri?>(null) }
    var idBack by remember { mutableStateOf<Uri?>(null) }
    var licence by remember { mutableStateOf<Uri?>(null) }

    val launcherIdFront = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { idFront = it }
    val launcherIdBack = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { idBack = it }
    val launcherLicence = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { licence = it }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
        Spacer(Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Spacer(Modifier.width(4.dp))
            Column {
                Text("ID Documents", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("Step 3 of 4", color = TcTextMuted, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(4) { i -> Box(modifier = Modifier.height(3.dp).weight(1f).background(if (i < 3) TcGold else TcBorder, androidx.compose.foundation.shape.CircleShape)) }
        }
        Spacer(Modifier.height(16.dp))
        Text("Upload clear photos of your documents. All information is encrypted and stored securely.", color = TcTextMuted, fontSize = 13.sp)
        Spacer(Modifier.height(28.dp))

        DocumentUploadCard("National ID — Front", idFront, required = true) { launcherIdFront.launch("image/*") }
        Spacer(Modifier.height(14.dp))
        DocumentUploadCard("National ID — Back", idBack, required = true) { launcherIdBack.launch("image/*") }
        Spacer(Modifier.height(14.dp))
        DocumentUploadCard("Driver's Licence", licence, required = true) { launcherLicence.launch("image/*") }

        Spacer(Modifier.height(32.dp))
        TeddyButton("Continue", enabled = idFront != null && idBack != null && licence != null, onClick = onNext)
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Step 3: Vehicle Details ──────────────────────────────────────────────────

@Composable
fun RegistrationStep3Screen(onSubmit: () -> Unit, onBack: () -> Unit) {
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var regCert by remember { mutableStateOf<Uri?>(null) }
    var insurance by remember { mutableStateOf<Uri?>(null) }

    val launcherReg = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { regCert = it }
    val launcherIns = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { insurance = it }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
        Spacer(Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TcTextPrimary) }
            Spacer(Modifier.width(4.dp))
            Column {
                Text("Vehicle Details", color = TcTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("Step 4 of 4", color = TcTextMuted, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(4) { Box(modifier = Modifier.height(3.dp).weight(1f).background(TcGold, androidx.compose.foundation.shape.CircleShape)) }
        }
        Spacer(Modifier.height(28.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.weight(1f)) { TeddyTextField(value = make, onValueChange = { make = it }, placeholder = "Make (e.g. Toyota)") }
            Box(modifier = Modifier.weight(1f)) { TeddyTextField(value = model, onValueChange = { model = it }, placeholder = "Model (e.g. Axio)") }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.weight(1f)) { TeddyTextField(value = year, onValueChange = { year = it }, placeholder = "Year", keyboardType = KeyboardType.Number) }
            Box(modifier = Modifier.weight(1f)) { TeddyTextField(value = color, onValueChange = { color = it }, placeholder = "Colour") }
        }
        Spacer(Modifier.height(12.dp))
        TeddyTextField(value = plate, onValueChange = { plate = it }, placeholder = "Licence plate (e.g. KCA 456B)", leadingIcon = Icons.Default.DirectionsCar)
        Spacer(Modifier.height(24.dp))

        SectionLabel("VEHICLE DOCUMENTS")
        Spacer(Modifier.height(14.dp))
        DocumentUploadCard("Registration Certificate (Logbook)", regCert, required = true) { launcherReg.launch("image/*") }
        Spacer(Modifier.height(14.dp))
        DocumentUploadCard("Insurance Certificate", insurance, required = true) { launcherIns.launch("image/*") }

        Spacer(Modifier.height(32.dp))
        Box(modifier = Modifier.fillMaxWidth().background(TcGoldFaint, RoundedCornerShape(12.dp)).border(1.dp, TcGold.copy(0.2f), RoundedCornerShape(12.dp)).padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, null, tint = TcGold, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Text("Your documents are reviewed by our team. Verification takes up to 24 hours.", color = TcTextMuted, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(24.dp))
        TeddyButton("Submit Application", enabled = plate.isNotEmpty() && make.isNotEmpty(), onClick = onSubmit)
        Spacer(Modifier.height(40.dp))
    }
}

// ─── Upload Card ──────────────────────────────────────────────────────────────

@Composable
private fun DocumentUploadCard(title: String, uri: Uri?, required: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().background(TcCard, RoundedCornerShape(14.dp))
            .border(1.dp, if (uri != null) TcGold.copy(0.4f) else TcBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(10.dp)).background(TcElevated),
                contentAlignment = Alignment.Center
            ) {
                if (uri != null) {
                    AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                } else {
                    Icon(Icons.Default.Upload, null, tint = TcTextMuted, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Text(title, color = TcTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    if (required) Text("  *", color = TcError, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Text(if (uri != null) "✓ Uploaded" else "Tap to upload", color = if (uri != null) TcSuccess else TcTextMuted, fontSize = 12.sp)
            }
            Icon(if (uri != null) Icons.Default.CheckCircle else Icons.Default.AddCircleOutline, null, tint = if (uri != null) TcSuccess else TcTextMuted, modifier = Modifier.size(22.dp))
        }
    }
}
