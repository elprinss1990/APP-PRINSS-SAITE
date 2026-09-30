package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.PackPlayer
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutGreen
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkContainer
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private val SAMPLE_PLAYERS = listOf(
    PackPlayer(
        name = "BELLINGHAM",
        rating = 94,
        position = "CAM",
        club = "Real Madrid",
        nation = "🏴󠁧󠁢󠁥󠁮󠁧󠁿 England",
        pace = 86,
        shooting = 90,
        passing = 92,
        dribbling = 94,
        defending = 85,
        physical = 91,
        cardType = "PRINSS TOTY"
    ),
    PackPlayer(
        name = "MBAPPÉ",
        rating = 96,
        position = "ST",
        club = "Real Madrid",
        nation = "🇫🇷 France",
        pace = 98,
        shooting = 95,
        passing = 88,
        dribbling = 96,
        defending = 42,
        physical = 86,
        cardType = "ULTIMATE ELITE"
    ),
    PackPlayer(
        name = "HAALAND",
        rating = 95,
        position = "ST",
        club = "Manchester City",
        nation = "🇳🇴 Norway",
        pace = 93,
        shooting = 97,
        passing = 75,
        dribbling = 87,
        defending = 50,
        physical = 96,
        cardType = "PRINSS TOTY"
    ),
    PackPlayer(
        name = "SALAH",
        rating = 93,
        position = "RW",
        club = "Liverpool",
        nation = "🇪🇬 Egypt",
        pace = 94,
        shooting = 92,
        passing = 89,
        dribbling = 93,
        defending = 52,
        physical = 81,
        cardType = "FC HEROES"
    )
)

enum class PackState {
    UNOPENED,
    OPENING,
    WALKOUT_NATION,
    WALKOUT_POSITION,
    WALKOUT_RATING,
    REVEALED
}

@Composable
fun FcCardRevealDialog(
    onDismiss: () -> Unit,
    onClaimReward: (Long) -> Unit
) {
    var packState by remember { mutableStateOf(PackState.UNOPENED) }
    var selectedPlayer by remember { mutableStateOf(SAMPLE_PLAYERS[0]) }
    val scope = rememberCoroutineScope()

    val shakeOffset = remember { Animatable(0f) }
    val cardFlip = remember { Animatable(0f) }
    val lightIntensity = remember { Animatable(0.2f) }
    val clueScale = remember { Animatable(0.5f) }
    val clueAlpha = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "stadiumLight")
    val beamAngle by infiniteTransition.animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "beamAngle"
    )

    fun startOpening() {
        scope.launch {
            packState = PackState.OPENING
            selectedPlayer = SAMPLE_PLAYERS.random()

            // Step 1: Intense Shake
            repeat(10) { i ->
                shakeOffset.animateTo(
                    targetValue = if (i % 2 == 0) 14f else -14f,
                    animationSpec = tween(50)
                )
            }
            shakeOffset.animateTo(0f, tween(50))

            // Step 2: Screen Flash & Stadium Beams
            lightIntensity.animateTo(1f, tween(150))

            // Walkout Clue 1: Nationality
            packState = PackState.WALKOUT_NATION
            clueScale.snapTo(1.6f)
            clueAlpha.snapTo(0f)
            launch { clueAlpha.animateTo(1f, tween(200)) }
            clueScale.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
            delay(650)

            // Walkout Clue 2: Position
            packState = PackState.WALKOUT_POSITION
            clueScale.snapTo(1.6f)
            clueAlpha.snapTo(0f)
            launch { clueAlpha.animateTo(1f, tween(200)) }
            clueScale.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
            delay(650)

            // Walkout Clue 3: Rating
            packState = PackState.WALKOUT_RATING
            clueScale.snapTo(1.8f)
            clueAlpha.snapTo(0f)
            launch { clueAlpha.animateTo(1f, tween(200)) }
            clueScale.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
            delay(750)

            // Step 3: Card Flip 3D
            packState = PackState.REVEALED
            cardFlip.snapTo(90f)
            cardFlip.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
            lightIntensity.animateTo(0.4f, tween(800))
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceDarkVoid.copy(alpha = 0.98f))
        ) {
            // Stadium Spotlights Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Draw stadium light beams
                val beamColor = when (packState) {
                    PackState.UNOPENED -> FutCyan.copy(alpha = 0.25f)
                    PackState.OPENING,
                    PackState.WALKOUT_NATION,
                    PackState.WALKOUT_POSITION,
                    PackState.WALKOUT_RATING -> FutGold.copy(alpha = lightIntensity.value.coerceAtLeast(0.6f))
                    PackState.REVEALED -> FutGreen.copy(alpha = 0.35f)
                }

                // Left beam
                drawLine(
                    brush = Brush.radialGradient(
                        colors = listOf(beamColor, Color.Transparent),
                        center = Offset(0f, 0f),
                        radius = w * 1.2f
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(w * 0.7f + beamAngle * 4f, h * 0.9f),
                    strokeWidth = w * 0.5f
                )

                // Right beam
                drawLine(
                    brush = Brush.radialGradient(
                        colors = listOf(beamColor, Color.Transparent),
                        center = Offset(w, 0f),
                        radius = w * 1.2f
                    ),
                    start = Offset(w, 0f),
                    end = Offset(w * 0.3f - beamAngle * 4f, h * 0.9f),
                    strokeWidth = w * 0.5f
                )
            }

            // Close button top right
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = TextPrimary
                )
            }

            // Main Content Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (packState) {
                    PackState.UNOPENED, PackState.OPENING -> {
                        Text(
                            text = "PRINSS FUT 27",
                            color = FutGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "ELITE CARD PACK REVEAL",
                            color = TextPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "Tap to open and reveal your FC 27 superstar card + coins reward",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Unopened Mystery Pack Card
                        Box(
                            modifier = Modifier
                                .size(width = 230.dp, height = 330.dp)
                                .graphicsLayer {
                                    translationX = shakeOffset.value
                                    rotationZ = shakeOffset.value * 0.4f
                                }
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF020617))
                                    )
                                )
                                .border(
                                    width = 2.dp,
                                    brush = Brush.sweepGradient(
                                        listOf(FutGold, FutCyan, FutPurple, FutGold)
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable(enabled = packState == PackState.UNOPENED) {
                                    startOpening()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "👑",
                                    fontSize = 48.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "FC 27",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 2.sp
                                )
                                Text(
                                    text = "PRINSS VIP PACK",
                                    color = FutCyan,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(FutGold)
                                        .padding(horizontal = 16.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = if (packState == PackState.OPENING) "OPENING..." else "TAP TO OPEN",
                                        color = SurfaceDarkVoid,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        if (packState == PackState.UNOPENED) {
                            Button(
                                onClick = { startOpening() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FutCyan
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(48.dp)
                                    .testTag("open_pack_button")
                            ) {
                                Text(
                                    text = "⚡ OPEN NOW",
                                    color = SurfaceDarkVoid,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }

                    PackState.WALKOUT_NATION -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                scaleX = clueScale.value
                                scaleY = clueScale.value
                                alpha = clueAlpha.value
                            }
                        ) {
                            Text(
                                text = "✦ WALKOUT",
                                color = FutCyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 3.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = selectedPlayer.nation,
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    PackState.WALKOUT_POSITION -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                scaleX = clueScale.value
                                scaleY = clueScale.value
                                alpha = clueAlpha.value
                            }
                        ) {
                            Text(
                                text = "✦ POSITION",
                                color = FutCyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 3.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = selectedPlayer.position,
                                color = FutGold,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    PackState.WALKOUT_RATING -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                scaleX = clueScale.value
                                scaleY = clueScale.value
                                alpha = clueAlpha.value
                            }
                        ) {
                            Text(
                                text = "✦ RATING",
                                color = FutCyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 3.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = selectedPlayer.rating.toString(),
                                color = FutGold,
                                fontSize = 68.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    PackState.REVEALED -> {
                        // The Revealed EA FC 27 FUTCard with 3D flip animation & custom slots
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationY = cardFlip.value
                                    cameraDistance = 18f * density
                                }
                        ) {
                            FUTCard(
                                player = selectedPlayer,
                                size = FUTCardSize.STANDARD,
                                imageResId = if (selectedPlayer.name.contains("BELLINGHAM", ignoreCase = true)) {
                                    com.example.R.drawable.hero_fc27_bellingham
                                } else null
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Reward Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceDarkContainer)
                                .border(1.dp, FutGreen.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "🎉 CONGRATULATIONS!",
                                    color = FutGreen,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "You won 100,000 Free FC 27 Bonus Coins!",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    onClaimReward(100000L)
                                    onDismiss()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = FutGreen
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1.2f)
                                    .height(48.dp)
                                    .testTag("claim_pack_reward_button")
                            ) {
                                Text(
                                    text = "رجوع للمتجر ✓ (+100K)",
                                    color = SurfaceDarkVoid,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp
                                )
                            }

                            Button(
                                onClick = { startOpening() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceDarkContainer
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(0.9f)
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = "افتح تاني ⚡",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            color = FutGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
