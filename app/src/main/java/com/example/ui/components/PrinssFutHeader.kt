package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun PrinssFutHeader(
    cartCount: Int,
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDarkVoid.copy(alpha = 0.95f))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu Icon
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, CircleShape)
                    .testTag("header_menu_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = TextPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // PRINSS FUT Brand
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable { onSearchQueryChange("") }
            ) {
                // Crown symbol in gold
                Text(
                    text = "👑",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "PRINSS",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "FUT",
                            color = FutGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "PLAY MORE • PAY LESS",
                        color = FutCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }
            }

            // Right Actions: Search + Cart
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { isSearchOpen = !isSearchOpen },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark1)
                        .border(1.dp, SurfaceDarkBorder, CircleShape)
                        .testTag("header_search_button")
                ) {
                    Icon(
                        imageVector = if (isSearchOpen) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark1)
                        .border(1.dp, SurfaceDarkBorder, CircleShape)
                        .testTag("header_cart_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (cartCount > 0) {
                                Badge(
                                    containerColor = Color(0xFFEF4444),
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = cartCount.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = if (cartCount > 0) FutCyan else TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Live Delivery status bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(FutGreen.copy(alpha = pulseAlpha))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "⚡ Real-time EA FC 27 Coin Delivery: ~2-5 mins avg transfer",
                color = FutGreen,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        AnimatedVisibility(visible = isSearchOpen) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDarkContainer)
                    .border(1.dp, FutCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        onSearchQueryChange(it)
                    },
                    placeholder = {
                        Text(
                            text = "Search coins, SBC, Rivals, PS Plus, gift cards...",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
