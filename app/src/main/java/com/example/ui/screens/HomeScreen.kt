package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.model.PackPlayer
import com.example.model.Platform
import com.example.ui.components.FUTCard
import com.example.ui.components.FUTCardSize
import com.example.ui.components.NavTab
import com.example.ui.components.PrinssCoinCalculator
import com.example.ui.components.PrinssTickerBar
import com.example.ui.components.ReviewsAndCreatorsSection
import com.example.ui.components.SellCoinsSection
import com.example.ui.components.WhyPrinssSection
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
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedPlatform by viewModel.selectedPlatform.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. HUD & Hero Banner matching Elementor Canvas
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF140D26), Color(0xFF0F0B1E), Color(0xFF07050D))
                        )
                    )
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(PrinssViolet, PrinssMagenta, PrinssCyan)),
                        RoundedCornerShape(22.dp)
                    )
                    .testTag("home_hero_banner")
            ) {
                // Background hero image (Bellingham FC 27 style)
                Image(
                    painter = painterResource(id = R.drawable.hero_fc27_bellingham),
                    contentDescription = "FC 27 Bellingham Banner",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Crop,
                    alpha = 0.35f
                )

                // Dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    PrinssBg.copy(alpha = 0.95f),
                                    PrinssBg.copy(alpha = 0.8f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Content inside Hero
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    // HUD Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0x33FFFFFF),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
                        ) {
                            Text(
                                text = "EA FC 27 · ULTIMATE TEAM",
                                color = PrinssGold,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("SUPPORT 24/7", color = FutCyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("·", color = TextMuted, fontSize = 9.sp)
                            Text("BAN-SAFE ✓", color = FutGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "PRINSS FUT.",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "أفضل وجهة لشحن كوينز EA FC 27 وخدمات الألعاب الرقمية بسرعة وثقة",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "اطلب كوينز EA FC 27 لكل منصات PS4 / PS5 / Xbox / PC بضمان من البان والسعر الأقل.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons + Platform Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { viewModel.selectTab(NavTab.EA_FC_27) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(999.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .height(36.dp)
                                    .background(PrinssGrad, RoundedCornerShape(999.dp))
                            ) {
                                Text(
                                    text = "اشحن كوينز الآن ⚽",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Button(
                                onClick = { viewModel.selectTab(NavTab.GAMING) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(999.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    text = "كل الخدمات",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Supported Platform Pills
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("PS4", "PS5", "XBOX", "PC").forEach { plat ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0x44000000),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
                                ) {
                                    Text(
                                        text = plat,
                                        color = TextMuted,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Animated Ticker Bar
        item {
            PrinssTickerBar()
        }

        // 3. Interactive Coin Calculator (كام كوينز محتاج؟)
        item {
            PrinssCoinCalculator(
                currentPlatform = selectedPlatform,
                onPlatformSelect = { viewModel.selectPlatform(it) },
                onOrderCoins = { amount, platform ->
                    viewModel.addCoinsToCart(amount, platform)
                    viewModel.selectTab(NavTab.CART)
                }
            )
        }

        // 4. Featured FC 27 Cards Showcase (FUTCard Component)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⭐",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Featured FC 27 Cards",
                            color = TextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Text(
                        text = "Pack Arena →",
                        color = FutGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { viewModel.openPackReveal() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val featuredPlayers = listOf(
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
                        cardType = "TOTY"
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
                        cardType = "VIP ELITE"
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
                        cardType = "GOLD RARE"
                    ),
                    PackPlayer(
                        name = "ZIDANE",
                        rating = 97,
                        position = "CAM",
                        club = "Icons",
                        nation = "🇫🇷 France",
                        pace = 85,
                        shooting = 92,
                        passing = 97,
                        dribbling = 96,
                        defending = 78,
                        physical = 86,
                        cardType = "ICON"
                    ),
                    PackPlayer(
                        name = "SALAH",
                        rating = 93,
                        position = "RW",
                        club = "Liverpool",
                        nation = "🇪🇬 Egypt",
                        pace = 92,
                        shooting = 91,
                        passing = 88,
                        dribbling = 93,
                        defending = 47,
                        physical = 79,
                        cardType = "HERO"
                    )
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                ) {
                    items(featuredPlayers) { player ->
                        FUTCard(
                            player = player,
                            size = FUTCardSize.COMPACT,
                            imageResId = if (player.name.contains("BELLINGHAM")) {
                                R.drawable.hero_fc27_bellingham
                            } else null,
                            onClick = { viewModel.openPackReveal() }
                        )
                    }
                }
            }
        }

        // 5. Pack Reveal Promo Banner (جرّب حظك… افتح الباك)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF2E1065), Color(0xFF0F172A))
                        )
                    )
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(FutGold, PrinssMagenta)),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable { viewModel.openPackReveal() }
                    .padding(16.dp)
                    .testTag("home_card_reveal_teaser")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "🎁",
                            fontSize = 34.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "جرّب حظك… افتح الباك ⚡",
                                color = FutGold,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "اضغط واستنى الـ Walkout — العلم، المركز، والتقييم واكسب كوينز مجانية!",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FutGold)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "افتح الآن",
                            color = SurfaceDarkVoid,
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // 6. All EA FC 27 Services Grid (matching the 11 services on prinssfut.com)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "جميع خدمات EA FC 27 متوفرة هنا",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "كل الخدمات →",
                        color = FutCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { viewModel.selectTab(NavTab.EA_FC_27) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Top 2 Primary Services: Buy Coins & Sell Coins
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ServiceTileCard(
                        titleAr = "شراء كوينز FC 27",
                        titleEn = "BUY COINS",
                        icon = Icons.Default.Bolt,
                        iconTint = FutGold,
                        onClick = { viewModel.selectTab(NavTab.EA_FC_27) },
                        modifier = Modifier.weight(1f)
                    )
                    ServiceTileCard(
                        titleAr = "بيع كوينز FC 27",
                        titleEn = "SELL COINS",
                        icon = Icons.Default.AutoAwesome,
                        iconTint = PrinssMagenta,
                        onClick = { viewModel.selectTab(NavTab.EA_FC_27) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 4 Specialized Competitive Services
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MiniServiceCard(
                        titleAr = "الفوت شامبيون",
                        titleEn = "FUT CHAMPIONS",
                        icon = Icons.Default.MilitaryTech,
                        iconTint = FutGold,
                        onClick = { viewModel.addServiceToCart("FUT Champions 20-0 Boost", 950.0, selectedPlatform) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "ديفيجن رايفلز",
                        titleEn = "RIVALS",
                        icon = Icons.Default.Bolt,
                        iconTint = FutCyan,
                        onClick = { viewModel.addServiceToCart("Division Rivals Boost", 600.0, selectedPlatform) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "تحديات SBC",
                        titleEn = "SBC TASKS",
                        icon = Icons.Default.Group,
                        iconTint = FutPurple,
                        onClick = { viewModel.addServiceToCart("SBC Solutions Completion", 450.0, selectedPlatform) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "أدوات السنايب",
                        titleEn = "SNIPE TOOLS",
                        icon = Icons.Default.AutoAwesome,
                        iconTint = FutGreen,
                        onClick = { viewModel.addServiceToCart("Snipe Tool License", 350.0, selectedPlatform) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Gaming Subscriptions & Store Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MiniServiceCard(
                        titleAr = "بلايستيشن بلس",
                        titleEn = "PS PLUS",
                        icon = Icons.Default.SportsEsports,
                        iconTint = FutCyan,
                        onClick = { viewModel.selectTab(NavTab.GAMING) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "بطاقات ستور PSN",
                        titleEn = "PSN CARDS",
                        icon = Icons.Default.CardGiftcard,
                        iconTint = FutGold,
                        onClick = { viewModel.selectTab(NavTab.GAMING) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "بطاقات إكس بوكس",
                        titleEn = "XBOX CARDS",
                        icon = Icons.Default.SportsEsports,
                        iconTint = FutGreen,
                        onClick = { viewModel.selectTab(NavTab.GAMING) },
                        modifier = Modifier.weight(1f)
                    )
                    MiniServiceCard(
                        titleAr = "ألعاب كونسول",
                        titleEn = "GAMES PS5",
                        icon = Icons.Default.SportsEsports,
                        iconTint = PrinssMagenta,
                        onClick = { viewModel.selectTab(NavTab.GAMING) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 7. Why PRINSS FUT Section
        item {
            WhyPrinssSection()
        }

        // 8. Reviews & Content Creators Section
        item {
            ReviewsAndCreatorsSection()
        }

        // 9. Sell Your Coins Section
        item {
            SellCoinsSection(
                onNavigateCoins = { viewModel.selectTab(NavTab.EA_FC_27) }
            )
        }
    }
}

@Composable
private fun ServiceTileCard(
    titleAr: String,
    titleEn: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrinssPanel),
        modifier = modifier
            .border(1.dp, iconTint.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.15f))
                    .border(1.dp, iconTint.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = titleEn,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titleAr,
                color = TextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = titleEn,
                color = TextMuted,
                fontSize = 9.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MiniServiceCard(
    titleAr: String,
    titleEn: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PrinssPanel),
        modifier = modifier
            .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconTint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = titleEn,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = titleAr,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                text = titleEn,
                color = TextMuted,
                fontSize = 7.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
