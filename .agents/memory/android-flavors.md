---
name: Teddy Cabs Android Flavor Architecture
description: How the passenger/driver APK split is structured using Gradle product flavors
---

# Flavor APK Pattern

**Rule:** Single `MainActivity` in `main` source set calls `AppContent()`. Each flavor (`passenger`, `driver`) defines its own `AppContent()` composable that wires into its own NavGraph.

**Why:** Cleanest way to produce two completely different APKs from one codebase without duplicating activity/manifest boilerplate.

**How to apply:**
- Shared code (theme, components, models) lives in `main` source set
- `app/src/passenger/java/com/example/AppContent.kt` → calls `PassengerNavGraph()`
- `app/src/driver/java/com/example/AppContent.kt` → calls `DriverNavGraph()`
- All passenger screens in `app/src/passenger/java/com/example/ui/passenger/`
- All driver screens in `app/src/driver/java/com/example/ui/driver/`
- Flavor manifests only override `android:label` via `resValue("string", "app_name", ...)`
- Google Sign-In uses `play-services-auth:21.3.0` with `GoogleSignIn` + `rememberLauncherForActivityResult`
- Document upload uses `ActivityResultContracts.GetContent()` + Coil `AsyncImage`
- OTP: single hidden `BasicTextField` + visual `OtpInputRow` overlay pattern

**Build commands:**
- Passenger debug APK: `./gradlew assemblePassengerDebug`
- Driver debug APK: `./gradlew assembleDriverDebug`

**Key app IDs:**
- passenger: `com.teddycabs.passenger`
- driver: `com.teddycabs.driver`

**Web:** Admin at `/admin`, support at `/support` — not linked anywhere on public pages. Accessible only via direct URL.
