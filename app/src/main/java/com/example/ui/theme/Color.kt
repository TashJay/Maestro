package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

val GoldPrimary = Color(0xFFD4AF37)
val GoldSecondary = Color(0xFFC5A017)
val GoldTertiary = Color(0xFFE5C048)

val MetallicGoldLight = Color(0xFFFFDF73)
val MetallicGoldDark = Color(0xFFB8860B)

val BlackBackground = Color(0xFF0A0A0B) // Deep charcoal navy/black
val BlackSurface = Color(0xFF1C1C1E) // Softer dark gray
val GrayText = Color(0xFFAAAAAA)
val CreamText = Color(0xFFFDFBF7)

val GoldGradient = Brush.linearGradient(
    colors = listOf(MetallicGoldDark, MetallicGoldLight, GoldPrimary, MetallicGoldDark)
)
