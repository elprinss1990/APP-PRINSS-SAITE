package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkVoid
import kotlin.random.Random

private data class Particle(
    val xRatio: Float,
    val yRatio: Float,
    val size: Float,
    val speed: Float,
    val alpha: Float,
    val color: Color
)

@Composable
fun AtmosphericArenaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    val particles = remember {
        val colors = listOf(FutCyan, FutPurple, Color(0xFF00FF88), Color(0xFFFFC72C))
        List(28) {
            Particle(
                xRatio = Random.nextFloat(),
                yRatio = Random.nextFloat(),
                size = Random.nextFloat() * 2.5f + 1.2f,
                speed = Random.nextFloat() * 0.4f + 0.1f,
                alpha = Random.nextFloat() * 0.4f + 0.15f,
                color = colors[it % colors.size]
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDarkVoid)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Top-left subtle cyan stadium spotlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x1800F0FF), Color.Transparent),
                    center = Offset(width * 0.2f, height * 0.08f),
                    radius = width * 0.85f
                ),
                center = Offset(width * 0.2f, height * 0.08f),
                radius = width * 0.85f
            )

            // Right-side subtle purple arena spotlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x158B5CF6), Color.Transparent),
                    center = Offset(width * 0.85f, height * 0.45f),
                    radius = width * 0.75f
                ),
                center = Offset(width * 0.85f, height * 0.45f),
                radius = width * 0.75f
            )

            // Floating cyber particles & digital arena embers
            particles.forEach { p ->
                val currentY = ((p.yRatio - (time * p.speed)) % 1f + 1f) % 1f
                val drawX = p.xRatio * width
                val drawY = currentY * height
                drawCircle(
                    color = p.color.copy(alpha = p.alpha),
                    radius = p.size,
                    center = Offset(drawX, drawY)
                )
            }
        }

        content()
    }
}

/**
 * Provides smooth 3D perspective tilt on drag or touch
 */
fun Modifier.interactiveTilt(
    maxTiltDegrees: Float = 8f
): Modifier = this.pointerInput(Unit) {
    var tiltX by mutableFloatStateOf(0f)
    var tiltY by mutableFloatStateOf(0f)

    detectDragGestures(
        onDragEnd = {
            tiltX = 0f
            tiltY = 0f
        },
        onDragCancel = {
            tiltX = 0f
            tiltY = 0f
        },
        onDrag = { change, dragAmount ->
            change.consume()
            tiltX = (tiltX + dragAmount.y * 0.05f).coerceIn(-maxTiltDegrees, maxTiltDegrees)
            tiltY = (tiltY - dragAmount.x * 0.05f).coerceIn(-maxTiltDegrees, maxTiltDegrees)
        }
    )
}.graphicsLayer {
    cameraDistance = 16f * density
}
