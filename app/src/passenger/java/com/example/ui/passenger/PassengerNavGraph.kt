package com.example.ui.passenger

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PassengerNavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            PassengerSplashScreen(onDone = { nav.navigate("onboarding") { popUpTo("splash") { inclusive = true } } })
        }
        composable("onboarding") {
            PassengerOnboardingScreen(
                onGetStarted = { nav.navigate("login") { popUpTo("onboarding") { inclusive = true } } }
            )
        }
        composable("login") {
            PassengerLoginScreen(
                onLoginSuccess = { nav.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignup = { nav.navigate("signup") },
                onGoogleSuccess = { nav.navigate("home") { popUpTo("login") { inclusive = true } } }
            )
        }
        composable("signup") {
            PassengerSignupScreen(
                onNavigateToOtp = { phone -> nav.navigate("otp/$phone") },
                onNavigateToLogin = { nav.popBackStack() },
                onGoogleSuccess = { nav.navigate("home") { popUpTo("signup") { inclusive = true } } }
            )
        }
        composable("otp/{phone}", arguments = listOf(navArgument("phone") { type = NavType.StringType })) { back ->
            val phone = back.arguments?.getString("phone") ?: ""
            PassengerOtpScreen(
                phone = phone,
                onVerified = { nav.navigate("home") { popUpTo("signup") { inclusive = true } } },
                onBack = { nav.popBackStack() }
            )
        }
        composable("home") {
            PassengerHomeScreen(
                onBookRide = { nav.navigate("book_ride") },
                onOpenHistory = { nav.navigate("history") },
                onOpenProfile = { nav.navigate("profile") }
            )
        }
        composable("book_ride") {
            PassengerBookRideScreen(
                onRideRequested = { nav.navigate("tracking") { popUpTo("home") } },
                onBack = { nav.popBackStack() }
            )
        }
        composable("tracking") {
            PassengerTrackingScreen(
                onTripComplete = { nav.navigate("payment") { popUpTo("tracking") { inclusive = true } } },
                onCancel = { nav.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
        composable("payment") {
            PassengerPaymentScreen(
                onPaymentDone = { nav.navigate("rating") { popUpTo("payment") { inclusive = true } } }
            )
        }
        composable("rating") {
            PassengerRatingScreen(
                onSubmit = { nav.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
        composable("history") {
            PassengerHistoryScreen(onBack = { nav.popBackStack() })
        }
        composable("profile") {
            PassengerProfileScreen(
                onBack = { nav.popBackStack() },
                onLogout = { nav.navigate("onboarding") { popUpTo(0) { inclusive = true } } }
            )
        }
    }
}
