package com.example.ui.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

enum class NavTab(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    EA_FC_27("EA FC 27", Icons.Default.SportsSoccer),
    GAMING("Gaming", Icons.Default.SportsEsports),
    CART("Cart", Icons.Default.ShoppingCart),
    ACCOUNT("Orders", Icons.Default.Person)
}

@Composable
fun PrinssFutBottomNav(
    currentTab: NavTab,
    cartItemCount: Int,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDarkVoid)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(SurfaceDarkBorder, Color.Transparent)
                ),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(SurfaceDark1.copy(alpha = 0.95f))
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                val iconTint by animateColorAsState(
                    targetValue = if (isSelected) FutCyan else TextMuted,
                    animationSpec = tween(200),
                    label = "tabTint"
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("nav_tab_${tab.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BadgedBox(
                        badge = {
                            if (tab == NavTab.CART && cartItemCount > 0) {
                                Badge(
                                    containerColor = Color(0xFFEF4444),
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = cartItemCount.toString(),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        if (tab == NavTab.EA_FC_27) {
                            // Authentic UT badge icon
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) FutCyan.copy(alpha = 0.2f)
                                        else Color.Transparent
                                    )
                                    .border(
                                        width = 1.5.dp,
                                        color = if (isSelected) FutCyan else TextMuted,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "UT",
                                    color = if (isSelected) FutCyan else TextMuted,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        } else {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                tint = iconTint,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = tab.title,
                        color = if (isSelected) FutCyan else TextMuted,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )

                    // Active glow line under active item
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(width = 16.dp, height = 2.dp)
                                .clip(RoundedCornerShape(1.dp))
                                .background(FutCyan)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}
