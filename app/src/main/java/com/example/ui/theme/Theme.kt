package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val FutDarkColorScheme = darkColorScheme(
    primary = FutCyan,
    onPrimary = SurfaceDarkVoid,
    primaryContainer = SurfaceDarkContainer,
    onPrimaryContainer = FutCyan,
    secondary = FutPurple,
    onSecondary = Color.White,
    secondaryContainer = SurfaceDark2,
    onSecondaryContainer = Color.White,
    tertiary = FutGold,
    onTertiary = SurfaceDarkVoid,
    background = SurfaceDarkVoid,
    onBackground = TextPrimary,
    surface = SurfaceDark1,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceDark2,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceDarkBorder,
    outlineVariant = SurfaceDarkContainer
)

@Composable
fun PrinssFutTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SurfaceDarkVoid.toArgb()
            window.navigationBarColor = SurfaceDarkVoid.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = FutDarkColorScheme,
        typography = Typography,
        content = content
    )
}
