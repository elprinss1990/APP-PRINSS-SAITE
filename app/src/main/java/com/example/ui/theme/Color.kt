package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Surfaces from PRINSS FUT EA FC 27 Spec & Elementor Canvas
val PrinssBg = Color(0xFF07050D)
val PrinssPanel = Color(0xCC110B1F)
val PrinssLine = Color(0x14FFFFFF)
val PrinssLine2 = Color(0x26FFFFFF)
val PrinssMagenta = Color(0xFFFF2FB9) // --m: #ff2fb9
val PrinssViolet = Color(0xFF8B3DFF)  // --v: #8b3dff
val PrinssGold = Color(0xFFF7C96B)    // --gold: #f7c96b
val PrinssCyan = Color(0xFF3DF5FF)    // --cyan: #3df5ff
val PrinssGrad = Brush.linearGradient(
    listOf(Color(0xFF5B1CFF), Color(0xFFB23CFF), Color(0xFFFF2FB9))
)

val SurfaceDarkVoid = Color(0xFF07050D)
val SurfaceDark1 = Color(0xFF0E0B19)
val SurfaceDark2 = Color(0xFF130E22)
val SurfaceDarkBorder = Color(0xFF261D3B)
val SurfaceDarkContainer = Color(0xFF1B1430)
val SurfaceDarkGlass = Color(0xCC110B1F)

// Controlled Accent Colors inspired by EA FC interfaces & competitive gaming
val FutCyan = Color(0xFF00F0FF)
val FutCyanDark = Color(0xFF0099B8)
val FutGreen = Color(0xFF00FF88)
val FutGreenDark = Color(0xFF05B05B)
val FutPurple = Color(0xFF8B5CF6)
val FutPurpleDark = Color(0xFF6D28D9)
val FutGold = Color(0xFFFFC72C)
val FutGoldBright = Color(0xFFFFDE59)
val FutRed = Color(0xFFFF4343)
val FutOrange = Color(0xFFFF8A00)

// Platform Specific Accents
val PlayStationBlue = Color(0xFF0070D1)
val PlayStationBlueLight = Color(0xFF2997FF)
val XboxGreen = Color(0xFF107C10)
val SteamNavy = Color(0xFF171A21)
val EaAppOrange = Color(0xFFFF5200)

// Quick Checkout Brands
val WhatsAppGreen = Color(0xFF25D366)
val TelegramBlue = Color(0xFF24A1DE)

// Text & Status
val TextPrimary = Color(0xFFF9FAFB)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)

// Gradients
val FutGradientCyanPurple = Brush.horizontalGradient(
    listOf(Color(0xFF00E5FF), Color(0xFF9333EA))
)

val FutGradientPurpleCyan = Brush.horizontalGradient(
    listOf(Color(0xFF8B5CF6), Color(0xFF00E5FF))
)

val FutGradientCard = Brush.verticalGradient(
    listOf(Color(0xFF141C2B), Color(0xFF0C1017))
)

val FutGradientGold = Brush.linearGradient(
    listOf(Color(0xFFFFDF00), Color(0xFFD4AF37), Color(0xFF996515))
)

val FutGradientCyanGlow = Brush.radialGradient(
    listOf(Color(0x3300F0FF), Color(0x0000F0FF))
)
