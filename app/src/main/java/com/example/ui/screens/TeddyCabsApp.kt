package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun TeddyCabsApp() {
    val navController = rememberNavController()
    
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "onboarding",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(500)) },
            exitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(500)) },
            popEnterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(500)) },
            popExitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(500)) }
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onNavigateToLogin = { navController.navigate("login") },
                    onNavigateToSignup = { navController.navigate("signup") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { isDriver -> 
                        if (isDriver) navController.navigate("driver_dashboard")
                        else navController.navigate("passenger_dashboard")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("signup") {
                SignupScreen(
                    onSignupSuccess = { isDriver -> 
                        if (isDriver) navController.navigate("driver_dashboard")
                        else navController.navigate("passenger_dashboard")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("driver_dashboard") {
                DriverDashboardScreen(
                    onAcceptRide = { navController.navigate("ride_tracking/driver") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    onNavigateToHistory = { navController.navigate("history") },
                    onLogout = {
                        navController.navigate("onboarding") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
            composable("passenger_dashboard") {
                PassengerDashboardScreen(
                    onRequestRide = { navController.navigate("ride_tracking/passenger") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    onNavigateToHistory = { navController.navigate("history") },
                    onLogout = {
                        navController.navigate("onboarding") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("history") {
                TripHistoryScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("ride_tracking/{userType}") { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "passenger"
                RideTrackingScreen(
                    userType = userType,
                    onRideComplete = { navController.navigate("payment/$userType") },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable("payment/{userType}") { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "passenger"
                PaymentScreen(
                    userType = userType,
                    onPaymentComplete = { navController.navigate("rating/$userType") }
                )
            }
            composable("rating/{userType}") { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "passenger"
                RatingScreen(
                    userType = userType,
                    onRatingSubmit = {
                        if (userType == "driver") {
                            navController.navigate("driver_dashboard") {
                                popUpTo("driver_dashboard") { inclusive = true }
                            }
                        } else {
                            navController.navigate("passenger_dashboard") {
                                popUpTo("passenger_dashboard") { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}
