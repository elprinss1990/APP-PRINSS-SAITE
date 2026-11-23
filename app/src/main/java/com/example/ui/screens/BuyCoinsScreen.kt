package com.example.ui.screens

import com.example.model.WHATSAPP_NUMBER
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Platform
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutGreen
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkContainer
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val COIN_PRESETS = listOf(
    100_000L to "100K",
    500_000L to "500K",
    1_000_000L to "1M",
    2_000_000L to "2M",
    5_000_000L to "5M"
)

private val FREQUENTLY_BOUGHT = listOf(
    100_000L to "128 EGP",
    500_000L to "640 EGP",
    1_000_000L to "1,280 EGP",
    2_000_000L to "2,560 EGP",
    5_000_000L to "6,400 EGP"
)

@Composable
fun BuyCoinsScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    val selectedPlatform by viewModel.selectedPlatform.collectAsStateWithLifecycle()
    val coinsQuantity by viewModel.coinsQuantity.collectAsStateWithLifecycle()

    val minCoins = 100_000f
    val maxCoins = 30_000_000f

    val calculatedPrice = viewModel.calculateCoinPrice(coinsQuantity, selectedPlatform)
    val pricePerMillion = viewModel.getPricePerMillion(selectedPlatform)

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: < Back | Buy FC 27 Coins | (?) Help
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SurfaceDark1)
                            .border(1.dp, SurfaceDarkBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Buy FC 27 Coins",
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Safe • Fast • Reliable",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    IconButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "EA FC 27 Coins transferred via 100% Ban-Free Comfort Trade & Player Auction system."
                                )
                            }
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SurfaceDark1)
                            .border(1.dp, SurfaceDarkBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Help",
                            tint = TextSecondary
                        )
                    }
                }
            }

            // Platform Toggle: [PlayStation] [PC (EA App)]
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val isPs = selectedPlatform == Platform.PLAYSTATION
                    val isPc = selectedPlatform == Platform.PC

                    val psBrush = if (isPs) {
                        Brush.horizontalGradient(listOf(FutCyan, FutPurple))
                    } else {
                        Brush.linearGradient(listOf(SurfaceDark1, SurfaceDark1))
                    }

                    val pcBrush = if (isPc) {
                        Brush.horizontalGradient(listOf(FutCyan, FutPurple))
                    } else {
                        Brush.linearGradient(listOf(SurfaceDark1, SurfaceDark1))
                    }

                    // PlayStation
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(psBrush)
                            .border(
                                1.5.dp,
                                if (isPs) FutCyan else SurfaceDarkBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { viewModel.selectPlatform(Platform.PLAYSTATION) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🎮", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "PlayStation",
                                color = if (isPs) SurfaceDarkVoid else TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // PC
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(pcBrush)
                            .border(
                                1.5.dp,
                                if (isPc) FutCyan else SurfaceDarkBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { viewModel.selectPlatform(Platform.PC) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💻", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "PC (EA App)",
                                color = if (isPc) SurfaceDarkVoid else TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Current Price Ticker Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Current Price (${selectedPlatform.shortName})",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Updated",
                                    tint = FutCyan,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Updated 5 min ago",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(FutGold.copy(alpha = 0.2f))
                                    .border(1.5.dp, FutGold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "UT",
                                    color = FutGold,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "${numFormat.format(pricePerMillion.toInt())} EGP",
                                color = FutCyan,
                                fontWeight = FontWeight.Black,
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "per 1 Million Coins",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Select Quantity & Slider
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(18.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Select Quantity",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "1,000,000 = ${numFormat.format(pricePerMillion.toInt())} EGP",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "${numFormat.format(coinsQuantity)} Coins\n= ${numFormat.format(calculatedPrice.toInt())} EGP",
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Slider(
                            value = coinsQuantity.toFloat(),
                            onValueChange = { newVal ->
                                val stepped = ((newVal / 50_000).toInt() * 50_000L).coerceIn(100_000L, 30_000_000L)
                                viewModel.setCoinsQuantity(stepped)
                            },
                            valueRange = minCoins..maxCoins,
                            colors = SliderDefaults.colors(
                                thumbColor = FutCyan,
                                activeTrackColor = FutCyan,
                                inactiveTrackColor = SurfaceDarkContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("coins_quantity_slider")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("100K", color = TextMuted, fontSize = 10.sp)
                            Text("30M", color = TextMuted, fontSize = 10.sp)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            COIN_PRESETS.forEach { (amount, label) ->
                                val isSelected = coinsQuantity == amount
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) FutCyan else SurfaceDarkContainer)
                                        .clickable { viewModel.setCoinsQuantity(amount) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        color = if (isSelected) SurfaceDarkVoid else TextPrimary,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Price Breakdown Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Price per Million", color = TextSecondary, fontSize = 13.sp)
                            Text("${numFormat.format(pricePerMillion.toInt())} EGP", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Your Quantity", color = TextSecondary, fontSize = 13.sp)
                            Text(numFormat.format(coinsQuantity), color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }

                        Divider(
                            color = SurfaceDarkBorder,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Price", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(
                                text = "${numFormat.format(calculatedPrice.toInt())} EGP",
                                color = FutGreen,
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }

            // Action Buttons: Add to Cart & Buy Now
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            viewModel.addCoinsToCart(coinsQuantity, selectedPlatform)
                            scope.launch {
                                snackbarHostState.showSnackbar("Added ${numFormat.format(coinsQuantity)} coins to Cart!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FutCyan),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("buy_coins_add_to_cart")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = SurfaceDarkVoid,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add to Cart",
                            color = SurfaceDarkVoid,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addCoinsToCart(coinsQuantity, selectedPlatform)
                            viewModel.openCheckout()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceDarkContainer),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .border(1.dp, FutCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                            .testTag("buy_coins_buy_now")
                    ) {
                        Text(
                            text = "⚡ Buy Now",
                            color = FutCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            // Why Buy From PRINSS FUT?
            item {
                Text(
                    text = "Why Buy From PRINSS FUT?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WhyBuyCard(
                        icon = Icons.Default.Bolt,
                        title = "Fast\nDelivery",
                        tint = FutCyan,
                        modifier = Modifier.weight(1f)
                    )
                    WhyBuyCard(
                        icon = Icons.Default.Security,
                        title = "100%\nSafe",
                        tint = FutGreen,
                        modifier = Modifier.weight(1f)
                    )
                    WhyBuyCard(
                        icon = Icons.Default.ThumbUp,
                        title = "Best\nPrices",
                        tint = FutGold,
                        modifier = Modifier.weight(1f)
                    )
                    WhyBuyCard(
                        icon = Icons.Default.Headphones,
                        title = "24/7\nSupport",
                        tint = FutPurple,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Checkout (WhatsApp & Telegram)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Quick Checkout",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            val msg = "Hello PRINSS FUT, I would like to buy ${numFormat.format(coinsQuantity)} FC 27 Coins for ${selectedPlatform.displayName} at ${numFormat.format(calculatedPrice.toInt())} EGP."
                            val url = "https://wa.me/$WHATSAPP_NUMBER?text=${Uri.encode(msg)}"
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            } catch (e: Exception) {}
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("coins_whatsapp_button")
                    ) {
                        Text("💬", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Order via WhatsApp",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = {
                            val msg = "Hello PRINSS FUT, I would like to buy ${numFormat.format(coinsQuantity)} FC 27 Coins (${selectedPlatform.displayName})."
                            val url = "https://t.me/PrinssFut?text=${Uri.encode(msg)}"
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            } catch (e: Exception) {}
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("coins_telegram_button")
                    ) {
                        Text("✈️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Order via Telegram",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Frequently Bought List
            item {
                Text(
                    text = "Frequently Bought",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    items(FREQUENTLY_BOUGHT) { (coins, price) ->
                        val isSelected = coinsQuantity == coins
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) FutCyan.copy(alpha = 0.2f) else SurfaceDark1)
                                .border(
                                    1.dp,
                                    if (isSelected) FutCyan else SurfaceDarkBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { viewModel.setCoinsQuantity(coins) }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val label = when {
                                    coins >= 1_000_000 -> "${coins / 1_000_000}M"
                                    else -> "${coins / 1_000}K"
                                }
                                Text(
                                    text = label,
                                    color = if (isSelected) FutCyan else TextPrimary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = price,
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        )
    }
}

@Composable
private fun WhyBuyCard(
    icon: ImageVector,
    title: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceDark1)
            .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                color = TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 13.sp
            )
        }
    }
}
