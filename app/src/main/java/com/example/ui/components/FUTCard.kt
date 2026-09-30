package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PackPlayer
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutGreen
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkContainer
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

/**
 * Authentic EA FC 27 Shield Shape featuring angled chamfered top shoulders,
 * straight vertical flanks, and a tapered chevron bottom crest.
 */
class FUTCardShieldShape(
    private val topChamferPercent: Float = 0.14f,
    private val bottomTaperPercent: Float = 0.15f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val w = size.width
        val h = size.height
        val cX = w * topChamferPercent
        val cY = cX * 0.75f
        val bY = h * (1f - bottomTaperPercent)

        val path = Path().apply {
            moveTo(cX, 0f)
            lineTo(w - cX, 0f)
            lineTo(w, cY)
            lineTo(w, bY)
            lineTo(w * 0.5f, h)
            lineTo(0f, bY)
            lineTo(0f, cY)
            close()
        }
        return Outline.Generic(path)
    }
}

val DefaultFUTCardShape: Shape = FUTCardShieldShape()

/**
 * Visual themes inspired by official EA SPORTS FC 27 Ultimate Team card designs.
 */
enum class FUTCardType(
    val title: String,
    val backgroundBrush: Brush,
    val borderBrush: Brush,
    val ratingColor: Color,
    val accentColor: Color,
    val statsContainerColor: Color,
    val statsDividerColor: Color,
    val hologramSheen: Color,
    val badgeLabel: String
) {
    TOTY(
        title = "Team of the Year",
        backgroundBrush = Brush.verticalGradient(
            listOf(Color(0xFF1E1B4B), Color(0xFF0F172A), Color(0xFF030712))
        ),
        borderBrush = Brush.linearGradient(
            listOf(Color(0xFFFFDF00), Color(0xFF00F0FF), Color(0xFF9333EA), Color(0xFFFFDF00))
        ),
        ratingColor = Color(0xFFFFDF00),
        accentColor = Color(0xFF00F0FF),
        statsContainerColor = Color(0xF00A0E17),
        statsDividerColor = Color(0x3300F0FF),
        hologramSheen = Color(0x3500F0FF),
        badgeLabel = "TOTY 27"
    ),
    GOLD_RARE(
        title = "Gold Rare",
        backgroundBrush = Brush.verticalGradient(
            listOf(Color(0xFF2E2211), Color(0xFF19130A), Color(0xFF0A0805))
        ),
        borderBrush = Brush.linearGradient(
            listOf(Color(0xFFFFE57F), Color(0xFFD4AF37), Color(0xFFAA7C11), Color(0xFFFFE57F))
        ),
        ratingColor = Color(0xFFFFDE59),
        accentColor = Color(0xFFFFC72C),
        statsContainerColor = Color(0xF014100B),
        statsDividerColor = Color(0x33FFC72C),
        hologramSheen = Color(0x30FFDE59),
        badgeLabel = "GOLD RARE"
    ),
    ULTIMATE_ELITE(
        title = "PRINSS VIP Elite",
        backgroundBrush = Brush.verticalGradient(
            listOf(Color(0xFF052A2D), Color(0xFF0B1622), Color(0xFF04070D))
        ),
        borderBrush = Brush.linearGradient(
            listOf(Color(0xFF00F0FF), Color(0xFF00FF88), Color(0xFF0099B8), Color(0xFF00F0FF))
        ),
        ratingColor = Color(0xFF00F0FF),
        accentColor = Color(0xFF00FF88),
        statsContainerColor = Color(0xF008121A),
        statsDividerColor = Color(0x3300FF88),
        hologramSheen = Color(0x3800FF88),
        badgeLabel = "PRINSS VIP"
    ),
    ICONS(
        title = "Icons Classic",
        backgroundBrush = Brush.verticalGradient(
            listOf(Color(0xFF2B251B), Color(0xFF16130D), Color(0xFF0A0906))
        ),
        borderBrush = Brush.linearGradient(
            listOf(Color(0xFFFFF4BF), Color(0xFFD4AF37), Color(0xFF8A6517), Color(0xFFFFF4BF))
        ),
        ratingColor = Color(0xFFFFF4BF),
        accentColor = Color(0xFFE6C075),
        statsContainerColor = Color(0xF017130E),
        statsDividerColor = Color(0x33D4AF37),
        hologramSheen = Color(0x30FFF4BF),
        badgeLabel = "ICON"
    ),
    HEROES(
        title = "FC Heroes",
        backgroundBrush = Brush.verticalGradient(
            listOf(Color(0xFF3B0764), Color(0xFF1E0E38), Color(0xFF090412))
        ),
        borderBrush = Brush.linearGradient(
            listOf(Color(0xFFEC4899), Color(0xFF8B5CF6), Color(0xFF00F0FF), Color(0xFFEC4899))
        ),
        ratingColor = Color(0xFFF472B6),
        accentColor = Color(0xFFC084FC),
        statsContainerColor = Color(0xF0180B2B),
        statsDividerColor = Color(0x33EC4899),
        hologramSheen = Color(0x38EC4899),
        badgeLabel = "HERO"
    );

    companion object {
        fun fromCardType(name: String): FUTCardType {
            return when {
                name.contains("GOLD", ignoreCase = true) -> GOLD_RARE
                name.contains("ICON", ignoreCase = true) -> ICONS
                name.contains("HERO", ignoreCase = true) -> HEROES
                name.contains("VIP", ignoreCase = true) || name.contains("ELITE", ignoreCase = true) -> ULTIMATE_ELITE
                else -> TOTY
            }
        }
    }
}

/**
 * Sizing presets for responsive and varied card usage.
 */
enum class FUTCardSize(
    val width: Dp,
    val height: Dp,
    val ratingFontSize: TextUnit,
    val positionFontSize: TextUnit,
    val nameFontSize: TextUnit,
    val statValueFontSize: TextUnit,
    val statLabelFontSize: TextUnit,
    val badgePaddingH: Dp,
    val badgePaddingV: Dp
) {
    COMPACT(
        width = 175.dp,
        height = 265.dp,
        ratingFontSize = 26.sp,
        positionFontSize = 13.sp,
        nameFontSize = 14.sp,
        statValueFontSize = 12.sp,
        statLabelFontSize = 8.sp,
        badgePaddingH = 5.dp,
        badgePaddingV = 2.dp
    ),
    STANDARD(
        width = 270.dp,
        height = 410.dp,
        ratingFontSize = 38.sp,
        positionFontSize = 18.sp,
        nameFontSize = 20.sp,
        statValueFontSize = 16.sp,
        statLabelFontSize = 10.sp,
        badgePaddingH = 8.dp,
        badgePaddingV = 4.dp
    ),
    HERO(
        width = 310.dp,
        height = 470.dp,
        ratingFontSize = 46.sp,
        positionFontSize = 22.sp,
        nameFontSize = 24.sp,
        statValueFontSize = 18.sp,
        statLabelFontSize = 12.sp,
        badgePaddingH = 10.dp,
        badgePaddingV = 5.dp
    )
}

/**
 * Standard 6 FUT Player Card Stats
 */
data class FUTStats(
    val pace: Int,
    val shooting: Int,
    val passing: Int,
    val dribbling: Int,
    val defending: Int,
    val physical: Int
)

/**
 * Primary FUTCard Composable mimicking the EA SPORTS FC 27 player card aesthetic.
 *
 * Provides dedicated Composable slots for:
 * - [playerImage]: Player image, portrait or silhouette render
 * - [overallRating]: Rating value, position, nation and club elements
 * - [statsContent]: Stats grid/row composed with Material 3 components
 * - [nameContent]: Player name label
 * - [headerContent]: Optional edition or tournament banner (e.g. TOTY, ICON)
 * - [playstylesContent]: Optional EA FC 27 PlayStyles+ glyph row
 */
@Composable
fun FUTCard(
    modifier: Modifier = Modifier,
    cardType: FUTCardType = FUTCardType.TOTY,
    shape: Shape = DefaultFUTCardShape,
    size: FUTCardSize = FUTCardSize.STANDARD,
    elevation: Dp = 10.dp,
    borderWidth: Dp = 2.5.dp,
    isHolographic: Boolean = true,
    isInteractiveTilt: Boolean = true,
    onClick: (() -> Unit)? = null,
    // Slots
    playerImage: @Composable () -> Unit,
    overallRating: @Composable () -> Unit,
    statsContent: @Composable () -> Unit,
    nameContent: (@Composable () -> Unit)? = null,
    headerContent: (@Composable () -> Unit)? = null,
    playstylesContent: (@Composable () -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val tiltX = remember { Animatable(0f) }
    val tiltY = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "fut_card_sheen")
    val sheenProgress by infiniteTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sheenProgress"
    )

    var cardModifier = modifier
        .size(width = size.width, height = size.height)
        .graphicsLayer {
            if (isInteractiveTilt) {
                rotationX = tiltX.value
                rotationY = tiltY.value
                cameraDistance = 16f * density
            }
        }

    if (isInteractiveTilt) {
        cardModifier = cardModifier.pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        val newX = (tiltX.value - dragAmount.y * 0.12f).coerceIn(-14f, 14f)
                        val newY = (tiltY.value + dragAmount.x * 0.12f).coerceIn(-14f, 14f)
                        tiltX.snapTo(newX)
                        tiltY.snapTo(newY)
                    }
                },
                onDragEnd = {
                    coroutineScope.launch {
                        tiltX.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = 400f))
                    }
                    coroutineScope.launch {
                        tiltY.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = 400f))
                    }
                },
                onDragCancel = {
                    coroutineScope.launch {
                        tiltX.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = 400f))
                    }
                    coroutineScope.launch {
                        tiltY.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = 400f))
                    }
                }
            )
        }
    }

    if (onClick != null) {
        cardModifier = cardModifier.clickable { onClick() }
    }

    // Material 3 Card container
    ElevatedCard(
        modifier = cardModifier.testTag("fut_card_root"),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation + 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardType.backgroundBrush)
                .border(width = borderWidth, brush = cardType.borderBrush, shape = shape)
                .drawBehind {
                    // EA FC 27 dynamic polygon texture lines
                    val w = this.size.width
                    val h = this.size.height
                    val strokeColor = cardType.accentColor.copy(alpha = 0.08f)
                    val stroke = Stroke(width = 1.2f)

                    // Diagonal futuristic geometry lines
                    drawLine(strokeColor, Offset(0f, h * 0.25f), Offset(w * 0.7f, 0f), strokeWidth = 1.2f)
                    drawLine(strokeColor, Offset(0f, h * 0.5f), Offset(w, h * 0.15f), strokeWidth = 1.2f)
                    drawLine(strokeColor, Offset(w * 0.3f, h), Offset(w, h * 0.65f), strokeWidth = 1.2f)
                    drawLine(strokeColor, Offset(0f, h * 0.8f), Offset(w * 0.8f, h), strokeWidth = 1.2f)

                    // Corner crest anchor points
                    drawCircle(cardType.accentColor.copy(alpha = 0.12f), radius = 24.dp.toPx(), center = Offset(w * 0.5f, h * 0.08f))
                }
        ) {
            // Ambient glow behind player image
            Box(
                modifier = Modifier
                    .size(size.width * 0.85f)
                    .align(Alignment.Center)
                    .background(
                        Brush.radialGradient(
                            listOf(cardType.accentColor.copy(alpha = 0.22f), Color.Transparent)
                        )
                    )
            )

            // Main Card Content Structure
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = if (size == FUTCardSize.COMPACT) 10.dp else 16.dp,
                        bottom = if (size == FUTCardSize.COMPACT) 12.dp else 18.dp,
                        start = if (size == FUTCardSize.COMPACT) 10.dp else 16.dp,
                        end = if (size == FUTCardSize.COMPACT) 10.dp else 16.dp
                    )
            ) {
                // Top Header Row: Rating slot on left, Header/Edition badge slot on right
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (size == FUTCardSize.COMPACT) 70.dp else 110.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Rating Slot
                    Box(modifier = Modifier.weight(1f)) {
                        overallRating()
                    }

                    // Header/Edition Slot
                    if (headerContent != null) {
                        headerContent()
                    } else {
                        // Default EA FC Edition Pill
                        FUTCardHeaderBadge(
                            label = cardType.badgeLabel,
                            cardType = cardType,
                            size = size
                        )
                    }
                }

                // Middle Section: Player Image Slot
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    playerImage()
                }

                // Optional PlayStyles+ Slot
                if (playstylesContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        playstylesContent()
                    }
                }

                // Player Name Slot
                if (nameContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (size == FUTCardSize.COMPACT) 4.dp else 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        nameContent()
                    }
                }

                // Stats Slot
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    statsContent()
                }
            }

            // Holographic Metallic Sheen Overlay
            if (isHolographic) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape)
                ) {
                    val w = this.size.width
                    val h = this.size.height
                    val currentX = sheenProgress * (w + h)
                    val sheenWidth = w * 0.45f

                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                cardType.hologramSheen,
                                Color.White.copy(alpha = 0.22f),
                                cardType.hologramSheen,
                                Color.Transparent
                            ),
                            start = Offset(currentX - sheenWidth, 0f),
                            end = Offset(currentX, h)
                        )
                    )
                }
            }
        }
    }
}

/**
 * Standard M3 Rating & Position component for the [overallRating] slot.
 */
@Composable
fun FUTOverallRating(
    rating: Int,
    position: String,
    modifier: Modifier = Modifier,
    cardType: FUTCardType = FUTCardType.TOTY,
    size: FUTCardSize = FUTCardSize.STANDARD,
    nation: String? = null,
    club: String? = null,
    nationContent: (@Composable () -> Unit)? = null,
    clubContent: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        // Overall Rating Number
        Text(
            text = rating.toString(),
            color = cardType.ratingColor,
            fontSize = size.ratingFontSize,
            fontWeight = FontWeight.Black,
            letterSpacing = (-0.5).sp,
            lineHeight = size.ratingFontSize
        )

        // Position Badge
        Text(
            text = position.uppercase(),
            color = TextPrimary,
            fontSize = size.positionFontSize,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(if (size == FUTCardSize.COMPACT) 2.dp else 4.dp))

        // Nation Flag / Content
        if (nationContent != null) {
            nationContent()
        } else if (!nation.isNullOrBlank()) {
            Text(
                text = nation,
                fontSize = if (size == FUTCardSize.COMPACT) 11.sp else 14.sp
            )
        }

        // Club Crest / Content
        if (clubContent != null) {
            clubContent()
        } else if (!club.isNullOrBlank()) {
            Text(
                text = club,
                color = TextSecondary,
                fontSize = if (size == FUTCardSize.COMPACT) 8.sp else 10.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Standard M3 Stats component for the [statsContent] slot displaying the 6 EA FC stats.
 */
@Composable
fun FUTCardStats(
    stats: FUTStats,
    modifier: Modifier = Modifier,
    cardType: FUTCardType = FUTCardType.TOTY,
    size: FUTCardSize = FUTCardSize.STANDARD
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (size == FUTCardSize.COMPACT) 8.dp else 12.dp),
        color = cardType.statsContainerColor,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            cardType.statsDividerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = if (size == FUTCardSize.COMPACT) 4.dp else 8.dp,
                    horizontal = if (size == FUTCardSize.COMPACT) 3.dp else 6.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatPill(label = "PAC", value = stats.pace, cardType = cardType, size = size)
            StatDivider(cardType.statsDividerColor, size)
            StatPill(label = "SHO", value = stats.shooting, cardType = cardType, size = size)
            StatDivider(cardType.statsDividerColor, size)
            StatPill(label = "PAS", value = stats.passing, cardType = cardType, size = size)
            StatDivider(cardType.statsDividerColor, size)
            StatPill(label = "DRI", value = stats.dribbling, cardType = cardType, size = size)
            StatDivider(cardType.statsDividerColor, size)
            StatPill(label = "DEF", value = stats.defending, cardType = cardType, size = size)
            StatDivider(cardType.statsDividerColor, size)
            StatPill(label = "PHY", value = stats.physical, cardType = cardType, size = size)
        }
    }
}

@Composable
private fun StatDivider(color: Color, size: FUTCardSize) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(if (size == FUTCardSize.COMPACT) 18.dp else 26.dp)
            .background(color)
    )
}

@Composable
private fun StatPill(
    label: String,
    value: Int,
    cardType: FUTCardType,
    size: FUTCardSize
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val valueColor = when {
            value >= 90 -> FutGreen
            value >= 80 -> cardType.ratingColor
            value >= 70 -> FutCyan
            else -> TextSecondary
        }

        Text(
            text = value.toString(),
            color = valueColor,
            fontSize = size.statValueFontSize,
            fontWeight = FontWeight.Black,
            lineHeight = size.statValueFontSize
        )
        Text(
            text = label,
            color = TextMuted,
            fontSize = size.statLabelFontSize,
            fontWeight = FontWeight.Bold,
            lineHeight = size.statLabelFontSize
        )
    }
}

/**
 * Standard M3 Edition Header Badge component.
 */
@Composable
fun FUTCardHeaderBadge(
    label: String,
    cardType: FUTCardType,
    size: FUTCardSize,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = cardType.accentColor.copy(alpha = 0.16f),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardType.accentColor.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = size.badgePaddingH,
                vertical = size.badgePaddingV
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = cardType.accentColor,
                modifier = Modifier.size(if (size == FUTCardSize.COMPACT) 9.dp else 12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = label.uppercase(),
                color = cardType.accentColor,
                fontSize = if (size == FUTCardSize.COMPACT) 8.sp else 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
        }
    }
}

/**
 * M3 PlayStyles+ row component for EA FC 27 abilities.
 */
@Composable
fun FUTPlaystylesRow(
    playstyles: List<String>,
    modifier: Modifier = Modifier,
    cardType: FUTCardType = FUTCardType.TOTY,
    size: FUTCardSize = FUTCardSize.STANDARD
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        playstyles.take(3).forEach { style ->
            Surface(
                shape = CircleShape,
                color = cardType.accentColor.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardType.accentColor.copy(alpha = 0.7f)),
                modifier = Modifier.size(if (size == FUTCardSize.COMPACT) 16.dp else 22.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = style,
                        tint = cardType.accentColor,
                        modifier = Modifier.size(if (size == FUTCardSize.COMPACT) 10.dp else 13.dp)
                    )
                }
            }
        }
    }
}

/**
 * High-level convenience overload of [FUTCard] taking a [PackPlayer] data model.
 */
@Composable
fun FUTCard(
    player: PackPlayer,
    modifier: Modifier = Modifier,
    cardType: FUTCardType = FUTCardType.fromCardType(player.cardType),
    shape: Shape = DefaultFUTCardShape,
    size: FUTCardSize = FUTCardSize.STANDARD,
    imagePainter: Painter? = null,
    imageResId: Int? = null,
    playstyles: List<String> = listOf("Finesse+", "Rapid+", "Technical"),
    onClick: (() -> Unit)? = null
) {
    val stats = remember(player) {
        FUTStats(
            pace = player.pace,
            shooting = player.shooting,
            passing = player.passing,
            dribbling = player.dribbling,
            defending = player.defending,
            physical = player.physical
        )
    }

    FUTCard(
        modifier = modifier,
        cardType = cardType,
        shape = shape,
        size = size,
        isHolographic = true,
        isInteractiveTilt = true,
        onClick = onClick,
        playerImage = {
            when {
                imagePainter != null -> {
                    Image(
                        painter = imagePainter,
                        contentDescription = player.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
                imageResId != null -> {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = player.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
                else -> {
                    // Default EA FC Dynamic Player Silhouette
                    Box(
                        modifier = Modifier
                            .size(if (size == FUTCardSize.COMPACT) 85.dp else 135.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(cardType.accentColor.copy(alpha = 0.35f), Color.Transparent)
                                )
                            )
                            .border(
                                1.5.dp,
                                Brush.linearGradient(
                                    listOf(cardType.accentColor, Color.Transparent, cardType.ratingColor)
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "⚡",
                                fontSize = if (size == FUTCardSize.COMPACT) 32.sp else 50.sp
                            )
                        }
                    }
                }
            }
        },
        overallRating = {
            FUTOverallRating(
                rating = player.rating,
                position = player.position,
                nation = player.nation,
                club = player.club,
                cardType = cardType,
                size = size
            )
        },
        statsContent = {
            FUTCardStats(
                stats = stats,
                cardType = cardType,
                size = size
            )
        },
        nameContent = {
            Text(
                text = player.name.uppercase(),
                color = TextPrimary,
                fontSize = size.nameFontSize,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        playstylesContent = {
            FUTPlaystylesRow(
                playstyles = playstyles,
                cardType = cardType,
                size = size
            )
        }
    )
}
