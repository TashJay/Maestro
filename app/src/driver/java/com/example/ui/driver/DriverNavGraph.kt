package com.example.ui.driver

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun DriverNavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            DriverSplashScreen(onDone = { nav.navigate("onboarding") { popUpTo("splash") { inclusive = true } } })
        }
        composable("onboarding") {
            DriverOnboardingScreen(onGetStarted = { nav.navigate("login") { popUpTo("onboarding") { inclusive = true } } })
        }
        composable("login") {
            DriverLoginScreen(
                onLoginSuccess = { nav.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignup = { nav.navigate("signup") },
                onGoogleSuccess = { nav.navigate("home") { popUpTo("login") { inclusive = true } } }
            )
        }
        composable("signup") {
            DriverSignupScreen(
                onNavigateToOtp = { phone -> nav.navigate("otp/$phone") },
                onNavigateToLogin = { nav.popBackStack() },
                onGoogleSuccess = { nav.navigate("register_1") { popUpTo("signup") { inclusive = true } } }
            )
        }
        composable("otp/{phone}", arguments = listOf(navArgument("phone") { type = NavType.StringType })) { back ->
            val phone = back.arguments?.getString("phone") ?: ""
            DriverOtpScreen(phone = phone, onVerified = { nav.navigate("register_1") { popUpTo("signup") { inclusive = true } } }, onBack = { nav.popBackStack() })
        }
        composable("register_1") {
            RegistrationStep1Screen(onNext = { nav.navigate("register_2") }, onBack = { nav.popBackStack() })
        }
        composable("register_2") {
            RegistrationStep2Screen(onNext = { nav.navigate("register_3") }, onBack = { nav.popBackStack() })
        }
        composable("register_3") {
            RegistrationStep3Screen(onSubmit = { nav.navigate("home") { popUpTo("register_1") { inclusive = true } } }, onBack = { nav.popBackStack() })
        }
        composable("home") {
            DriverHomeScreen(
                onAcceptRide = { nav.navigate("active_trip") },
                onOpenEarnings = { nav.navigate("earnings") },
                onOpenHistory = { nav.navigate("history") },
                onOpenProfile = { nav.navigate("profile") }
            )
        }
        composable("active_trip") {
            DriverActiveTripScreen(
                onTripComplete = { nav.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
        composable("earnings") {
            DriverEarningsScreen(onBack = { nav.popBackStack() })
        }
        composable("history") {
            DriverHistoryScreen(onBack = { nav.popBackStack() })
        }
        composable("profile") {
            DriverProfileScreen(onBack = { nav.popBackStack() }, onLogout = { nav.navigate("onboarding") { popUpTo(0) { inclusive = true } } })
        }
    }
}
