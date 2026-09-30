package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Platform
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutGreen
import com.example.ui.theme.FutPurple
import com.example.ui.theme.PrinssBg
import com.example.ui.theme.PrinssCyan
import com.example.ui.theme.PrinssGold
import com.example.ui.theme.PrinssGrad
import com.example.ui.theme.PrinssMagenta
import com.example.ui.theme.PrinssPanel
import com.example.ui.theme.PrinssViolet
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkContainer
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import kotlinx.coroutines.delay

/**
 * Continuous scrolling marquee ticker bar matching the Elementor canvas ticker.
 */
@Composable
fun PrinssTickerBar(modifier: Modifier = Modifier) {
    val tickerItems = listOf(
        "شحن كوينز EA FC 27",
        "PS4 / PS5 / Xbox / PC",
        "ضمان من البان",
        "الفوت شامبيون",
        "ديفيجن رايفلز",
        "تحديات SBC",
        "بلايستيشن بلس بخصومات حصرية",
        "بطاقات PSN و Xbox",
        "ألعاب PS4 و PS5 الأصلية",
        "دعم 24/7"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp),
        color = Color(0xCC0B0714),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1AFFFFFF))
    ) {
        val scrollState = rememberScrollState()

        LaunchedEffect(Unit) {
            while (true) {
                scrollState.animateScrollTo(
                    value = scrollState.maxValue,
                    animationSpec = tween(
                        durationMillis = 35000,
                        easing = LinearEasing
                    )
                )
                scrollState.scrollTo(0)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // Repeat twice for infinite effect
            repeat(3) {
                tickerItems.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "✦",
                            color = PrinssMagenta,
                            fontSize = 12.sp
                        )
                        Text(
                            text = item,
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Interactive Coin Calculator with 10 Stepped Gold Bars, Platform switcher,
 * and direct one-tap ordering matching the Elementor calculator.
 */
@Composable
fun PrinssCoinCalculator(
    currentPlatform: Platform,
    onPlatformSelect: (Platform) -> Unit,
    onOrderCoins: (Long, Platform) -> Unit,
    modifier: Modifier = Modifier
) {
    val steps = remember {
        listOf(100_000L, 250_000L, 500_000L, 750_000L, 1_000_000L, 1_500_000L, 2_000_000L, 3_000_000L, 4_000_000L, 5_000_000L)
    }

    var selectedStepIndex by remember { mutableIntStateOf(2) } // default 500k
    val currentAmount = steps[selectedStepIndex]

    val platformLabel = when (currentPlatform) {
        Platform.PLAYSTATION -> "PS4 / PS5"
        Platform.XBOX -> "XBOX ONE / SERIES"
        Platform.PC -> "PC (EA App)"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("prinss_coin_calculator"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PrinssPanel),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(PrinssMagenta)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "COIN CALCULATOR",
                        color = PrinssMagenta,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = "BAN-SAFE GUARANTEED",
                    color = FutGreen,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "كام كوينز محتاج؟",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "اختار منصتك واسحب المؤشر — شوف الكمية وروح للطلب مباشرة.",
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Platform Switcher Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlatformPill(
                    label = "PlayStation",
                    isSelected = currentPlatform == Platform.PLAYSTATION,
                    onClick = { onPlatformSelect(Platform.PLAYSTATION) },
                    modifier = Modifier.weight(1f)
                )
                PlatformPill(
                    label = "Xbox",
                    isSelected = currentPlatform == Platform.XBOX,
                    onClick = { onPlatformSelect(Platform.XBOX) },
                    modifier = Modifier.weight(1f)
                )
                PlatformPill(
                    label = "PC",
                    isSelected = currentPlatform == Platform.PC,
                    onClick = { onPlatformSelect(Platform.PC) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Big Amount Display
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "%,d".format(currentAmount),
                        color = TextPrimary,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "COINS",
                        color = PrinssGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                // Platform tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SurfaceDarkContainer,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF))
                ) {
                    Text(
                        text = platformLabel,
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated Visual Gold Bars Stack (matching #stack i from Elementor JS)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                for (i in 0 until 10) {
                    val isActive = i <= selectedStepIndex
                    val heightRatio = (20f + i * 8f) / 100f
                    val barBrush = if (isActive) {
                        Brush.verticalGradient(
                            listOf(Color(0xFFFFDF00), Color(0xFFD4AF37), Color(0xFF996515))
                        )
                    } else {
                        Brush.verticalGradient(
                            listOf(Color(0x33FFFFFF), Color(0x11FFFFFF))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(if (isActive) heightRatio else 0.12f)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(barBrush)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Stepped Slider
            Slider(
                value = selectedStepIndex.toFloat(),
                onValueChange = { selectedStepIndex = it.toInt() },
                valueRange = 0f..9f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = PrinssGold,
                    activeTrackColor = PrinssMagenta,
                    inactiveTrackColor = Color(0x33FFFFFF)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "100K", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(text = "1M", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(text = "5M", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security & Support Guarantees
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x66080512),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1FFFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("PLATFORM", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(platformLabel, color = PrinssGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("SAFETY", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("BAN-SAFE ✓", color = FutGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("SUPPORT", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("24/7 ✓", color = FutCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Call to Action Button with PrinssGrad
            Button(
                onClick = { onOrderCoins(currentAmount, currentPlatform) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(PrinssGrad, RoundedCornerShape(14.dp))
                    .testTag("calc_order_button")
            ) {
                Text(
                    text = "اطلب الكمية دي (${"%,d".format(currentAmount)} كوينز) ⚡",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun PlatformPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (isSelected) PrinssGrad else Brush.linearGradient(listOf(Color(0x22FFFFFF), Color(0x22FFFFFF))))
            .border(
                1.dp,
                if (isSelected) Color.Transparent else Color(0x26FFFFFF),
                RoundedCornerShape(999.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * 3 Core Value Props matching Why PRINSS FUT from Elementor:
 * - 24/7 SUPPORT
 * - TRUSTED & SECURE
 * - BEST VALUE
 */
@Composable
fun WhyPrinssSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(PrinssMagenta)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "WHY PRINSS FUT",
                color = PrinssMagenta,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "ليه متجر برنس الاختيار الأول؟",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WhyCard(
                iconEmoji = "🎧",
                tag = "24/7 SUPPORT",
                title = "دعم فني احترافي",
                desc = "نتابع طلبك خطوة بخطوة حتى اكتمال التنفيذ.",
                modifier = Modifier.weight(1f)
            )
            WhyCard(
                iconEmoji = "🛡️",
                tag = "TRUSTED & SECURE",
                title = "أمان كامل 100%",
                desc = "نحمي حسابك بمعايير أمان متقدمة وبدون أي مخاطرة.",
                modifier = Modifier.weight(1f)
            )
            WhyCard(
                iconEmoji = "💎",
                tag = "BEST VALUE",
                title = "أفضل قيمة وسعر",
                desc = "تحصل دائمًا على قيمة حقيقية مقابل ما تدفعه.",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WhyCard(
    iconEmoji: String,
    tag: String,
    title: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrinssPanel),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrinssGrad),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconEmoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = tag,
                color = PrinssMagenta,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                color = TextMuted,
                fontSize = 10.sp,
                lineHeight = 14.sp
            )
        }
    }
}

/**
 * Authentic Customer Testimonials from Gulf & Egypt + Content Creators list
 * matching the real reviews on prinssfut.com
 */
@Composable
fun ReviewsAndCreatorsSection(modifier: Modifier = Modifier) {
    val reviews = listOf(
        Pair("خدمة راقية وتفوق كل التوقعات", "طلال الفهد · الجهراء"),
        Pair("والله إنكم مذهلين في سرعة التوصيل", "فهد العتيبي · الرياض"),
        Pair("أنتم الأفضل في المملكة بدون مبالغة", "خالد السعد · الدمام"),
        Pair("التجربة الـ10 منهم وكل مرة دايمًا أفضل", "سعد العبدالجليل · حولي"),
        Pair("الخدمة سريعة والرد فوري يبارك فيكم", "محمد إبراهيم · الإسكندرية"),
        Pair("أحسن موقع من حيث الجودة", "عمر القيسي · عمان"),
        Pair("أفضل تعامل وخدمة ممتازة مضمونة", "علي الكواري · الدوحة"),
        Pair("مافيش منافس ليكم في السوق", "خالد حسين · الجيزة"),
        Pair("تجربة رائعة وأنصح الجميع بكم", "بدر الرشيد · الفروانية")
    )

    val creators = listOf(
        "mu_square",
        "aboel3abas",
        "MAHMOUD",
        "dr__tarekk",
        "walidinho_100",
        "شعلة",
        "mahmoud.diaaa",
        "kemoofahmey"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(PrinssMagenta)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "REAL REVIEWS",
                color = PrinssMagenta,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "عملاء راضين عن خدماتنا",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "جودة وسرعة تفوق الخيال — من الخليج لمصر.",
            color = TextSecondary,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Horizontal scrolling reviews carousel
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reviews) { (comment, author) ->
                Card(
                    modifier = Modifier.width(240.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrinssPanel),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "★★★★★",
                            color = PrinssGold,
                            fontSize = 13.sp,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = comment,
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = author,
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Creators trust line
        Text(
            text = "⭐ آراء حقيقية من فنانين وصناع محتوى وجيمرز وثقوا بخدماتنا ⭐",
            color = PrinssGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Creators chips row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(creators) { creator ->
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = Color(0x33000000),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x26FFFFFF))
                ) {
                    Text(
                        text = "@$creator",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

/**
 * "Sell your coins" (بيع لنا كوينز EA FC 27 بأفضل سعر) section with WhatsApp action
 */
@Composable
fun SellCoinsSection(
    onNavigateCoins: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun openWhatsApp() {
        val uri = Uri.parse("https://wa.me/96566838043?text=${Uri.encode("مرحباً متجر برنس فوت، أود بيع كوينز EA FC 27")}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PrinssPanel),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x26FFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(PrinssMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SELL YOUR COINS",
                    color = PrinssMagenta,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "بيع لنا كوينز EA FC 27 بأفضل سعر",
                color = TextPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "نشتري كوينز EA FC 27 لكل منصات PS4 / PS5 / Xbox / PC بضمان من البان ودفع فوري.",
                color = TextSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onNavigateCoins,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .background(PrinssGrad, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "بيع كوينز الآن 💰",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { openWhatsApp() },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Text(
                        text = "كلّمنا واتساب 💬",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
