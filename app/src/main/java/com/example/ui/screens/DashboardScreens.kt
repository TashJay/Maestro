package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.DriveEta
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import com.example.ui.components.PrimaryButton
import com.example.ui.components.SecondaryButton
import com.example.ui.components.SupportChatModal
import com.example.ui.components.TeddyTopAppBar
import com.example.ui.components.TeddyTextField
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerDashboardScreen(
    onRequestRide: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onLogout: () -> Unit
) {
    var pickup by remember { mutableStateOf("") }
    var dropoff by remember { mutableStateOf("") }
    var showSupportChat by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TeddyTopAppBar(
                title = "Teddy Cabs",
                actions = {
                    IconButton(onClick = { showSupportChat = true }) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Support Chat", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.History, contentDescription = "Trip History", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "Help", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (showSupportChat) {
                SupportChatModal(onDismiss = { showSupportChat = false })
            }
            
            // Simulated Map Area
            val london = LatLng(51.5072, -0.1276)
            val driver1 = LatLng(51.5100, -0.1300)
            val driver2 = LatLng(51.5050, -0.1200)
            val driver3 = LatLng(51.5200, -0.1350)
            
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(london, 14f)
            }
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF0A0A0B)),
                contentAlignment = Alignment.Center
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false)
                ) {
                    Marker(
                        state = MarkerState(position = london),
                        title = "Your Location",
                        snippet = "Pickup Here"
                    )
                    Marker(
                        state = MarkerState(position = driver1),
                        title = "Driver 1"
                    )
                    Marker(
                        state = MarkerState(position = driver2),
                        title = "Driver 2"
                    )
                    Marker(
                        state = MarkerState(position = driver3),
                        title = "Driver 3"
                    )
                }
            }

            // Booking Form
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                visible = true
            }
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(500)) + fadeIn(animationSpec = tween(600)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .background(Color(0xFF333335), RoundedCornerShape(2.dp))
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TeddyTextField(
                            value = pickup,
                            onValueChange = { pickup = it },
                            label = "Current Location",
                            leadingIcon = { Icon(Icons.Filled.TripOrigin, contentDescription = "Pickup", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TeddyTextField(
                            value = dropoff,
                            onValueChange = { dropoff = it },
                            label = "Where to?",
                            leadingIcon = { Icon(Icons.Filled.LocationOn, contentDescription = "Dropoff", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp)) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        var selectedVehicle by remember { mutableStateOf("premium") }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f).height(80.dp).clickable { selectedVehicle = "standard" },
                                colors = CardDefaults.cardColors(containerColor = if(selectedVehicle == "standard") Color(0xFF333333) else Color(0xFF252527)),
                                shape = RoundedCornerShape(12.dp),
                                border = if(selectedVehicle == "standard") androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
                            ) {
                                Column(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.DirectionsCar, contentDescription = "Standard", tint = if(selectedVehicle == "standard") MaterialTheme.colorScheme.primary else Color.Gray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Standard", color = if(selectedVehicle == "standard") Color.White else Color.Gray, fontSize = 12.sp)
                                    Text("$3.90", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                            Card(
                                modifier = Modifier.weight(1f).height(80.dp).clickable { selectedVehicle = "premium" },
                                colors = CardDefaults.cardColors(containerColor = if(selectedVehicle == "premium") Color(0xFF333333) else Color(0xFF252527)),
                                shape = RoundedCornerShape(12.dp),
                                border = if(selectedVehicle == "premium") androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
                            ) {
                                Column(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.DriveEta, contentDescription = "Premium", tint = if(selectedVehicle == "premium") MaterialTheme.colorScheme.primary else Color.Gray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Premium", color = if(selectedVehicle == "premium") Color.White else Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text("$6.50", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        PrimaryButton(text = "REQUEST ${selectedVehicle.uppercase()} RIDE", onClick = onRequestRide)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDashboardScreen(
    onAcceptRide: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onLogout: () -> Unit
) {
    var isOnline by remember { mutableStateOf(false) }
    var showSupportChat by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TeddyTopAppBar(
                title = "Driver Portal",
                actions = {
                    IconButton(onClick = { showSupportChat = true }) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Support Chat", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.History, contentDescription = "Trip History", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "Help", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (showSupportChat) {
                SupportChatModal(onDismiss = { showSupportChat = false })
            }
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF111111)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.simulated_map_bg_1779901350776),
                    contentDescription = "Map Background",
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedVisibility(
                        visible = isOnline,
                        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                    ) {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Searching for passengers...", color = MaterialTheme.colorScheme.primary, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AnimatedVisibility(
                        visible = isOnline,
                        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(500, delayMillis = 1500)) + fadeIn(animationSpec = tween(500, delayMillis = 1500)),
                        exit = shrinkOut() + fadeOut()
                    ) {
                        Card(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(0.9f),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("New Request", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("VIP Client - 5 mins away", color = Color.White)
                                Text("Destination: Airport", color = Color.LightGray, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                PrimaryButton(text = "Accept Ride", onClick = onAcceptRide)
                            }
                        }
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = !isOnline,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("You are currently offline.", color = Color.LightGray, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaddingValues(16.dp)
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Today's Earnings: KES 8,500", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        text = if (isOnline) "GO OFFLINE" else "GO ONLINE",
                        onClick = { isOnline = !isOnline }
                    )
                }
            }
        }
    }
}

