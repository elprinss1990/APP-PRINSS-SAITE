package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GamingCardItem
import com.example.model.Platform
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
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val GAMING_CATEGORIES = listOf("All", "PS Plus", "PS Cards", "Xbox", "EA FC Points", "PC Games")

private val SAMPLE_GAMING_CARDS = listOf(
    GamingCardItem("g1", "PlayStation Plus Extra 12 Months", Platform.PLAYSTATION, "12 Months Sub", 2250.0, "PS"),
    GamingCardItem("g2", "PlayStation Store $50 US Card", Platform.PLAYSTATION, "$50 USD Wallet", 2450.0, "PS"),
    GamingCardItem("g3", "PlayStation Store 100 SAR Saudi Card", Platform.PLAYSTATION, "100 SAR Wallet", 1320.0, "PS"),
    GamingCardItem("g4", "EA SPORTS FC 27 - 4,600 FC Points", Platform.PLAYSTATION, "4600 FC Points", 1850.0, "FC"),
    GamingCardItem("g5", "EA SPORTS FC 27 - 12,000 FC Points", Platform.PLAYSTATION, "12000 FC Points", 4600.0, "FC"),
    GamingCardItem("g6", "Xbox Game Pass Ultimate 3 Months", Platform.XBOX, "3 Months Pass", 1150.0, "XB"),
    GamingCardItem("g7", "Xbox $50 US Digital Gift Card", Platform.XBOX, "$50 USD", 2450.0, "XB"),
    GamingCardItem("g8", "EA SPORTS FC 27 Ultimate Edition PC Key", Platform.PC, "EA App Digital Key", 3100.0, "PC")
)

@Composable
fun GamingScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    var selectedCategory by remember { mutableStateOf("All") }

    val filteredCards = remember(selectedCategory) {
        when (selectedCategory) {
            "PS Plus" -> SAMPLE_GAMING_CARDS.filter { it.title.contains("PS Plus", ignoreCase = true) }
            "PS Cards" -> SAMPLE_GAMING_CARDS.filter { it.title.contains("PlayStation Store", ignoreCase = true) }
            "Xbox" -> SAMPLE_GAMING_CARDS.filter { it.platform == Platform.XBOX }
            "EA FC Points" -> SAMPLE_GAMING_CARDS.filter { it.title.contains("Points", ignoreCase = true) }
            "PC Games" -> SAMPLE_GAMING_CARDS.filter { it.platform == Platform.PC }
            else -> SAMPLE_GAMING_CARDS
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
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
                            text = "Gaming & Digital Cards",
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Instant delivery codes 24/7",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Box(modifier = Modifier.size(38.dp))
                }
            }

            // Categories horizontal scroller
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    items(GAMING_CATEGORIES) { cat ->
                        val isSelected = selectedCategory == cat
                        val catBrush = if (isSelected) {
                            Brush.horizontalGradient(listOf(FutCyan, FutPurple))
                        } else {
                            Brush.linearGradient(listOf(SurfaceDark1, SurfaceDark1))
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(catBrush)
                                .border(
                                    1.dp,
                                    if (isSelected) FutCyan else SurfaceDarkBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) SurfaceDarkVoid else TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Cards Grid items
            items(filteredCards, key = { it.id }) { item ->
                GamingItemCard(
                    item = item,
                    onAddToCart = {
                        viewModel.addServiceToCart(item.title, item.priceEgp, item.platform)
                        scope.launch {
                            snackbarHostState.showSnackbar("Added ${item.title} to Cart!")
                        }
                    }
                )
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
private fun GamingItemCard(
    item: GamingCardItem,
    onAddToCart: () -> Unit
) {
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    val badgeColor = when (item.iconType) {
        "PS" -> FutCyan
        "XB" -> FutGreen
        "FC" -> FutGold
        else -> FutPurple
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark1),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeColor.copy(alpha = 0.15f))
                    .border(1.dp, badgeColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.iconType,
                    color = badgeColor,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = item.denomination,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = "${numFormat.format(item.priceEgp.toInt())} EGP",
                    color = FutGreen,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceDarkContainer),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier
                    .height(38.dp)
                    .border(1.dp, FutCyan.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .testTag("gaming_card_add_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.AddShoppingCart,
                    contentDescription = "Add",
                    tint = FutCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Add",
                    color = FutCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
