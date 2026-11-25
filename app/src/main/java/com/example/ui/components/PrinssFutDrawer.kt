package com.example.ui.components

import com.example.model.WHATSAPP_NUMBER
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen

@Composable
fun PrinssFutDrawerContent(
    onNavigate: (NavTab) -> Unit,
    onOpenPackReveal: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(SurfaceDarkVoid)
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        // Drawer Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "👑", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row {
                        Text(
                            text = "PRINSS",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "FUT",
                            color = FutGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                    }
                    Text(
                        text = "VIP COMMAND CENTER",
                        color = FutCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            IconButton(
                onClick = onCloseDrawer,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(SurfaceDark1)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // VIP Status Pill
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDarkContainer)
                .border(1.dp, FutGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⭐", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "VIP Member Tier 1",
                        color = FutGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Comfort Trade ban protection enabled",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Menu Items
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DrawerItem(
                icon = Icons.Default.SportsSoccer,
                label = "EA FC 27 Coins Market",
                subtitle = "سوق كوينز إف سي 27",
                onClick = {
                    onNavigate(NavTab.EA_FC_27)
                    onCloseDrawer()
                }
            )
            DrawerItem(
                icon = Icons.Default.MonetizationOn,
                label = "Sell Coins to Us",
                subtitle = "بيع الكوينز بأعلى سعر",
                onClick = {
                    onNavigate(NavTab.EA_FC_27)
                    onCloseDrawer()
                }
            )
            DrawerItem(
                icon = Icons.Default.SportsEsports,
                label = "Gaming & Gift Cards",
                subtitle = "بطاقات بلايستيشن وإكس بوكس",
                onClick = {
                    onNavigate(NavTab.GAMING)
                    onCloseDrawer()
                }
            )
            DrawerItem(
                icon = Icons.Default.Security,
                label = "FC 27 Card Pack Reveal",
                subtitle = "فتح بكجات وكروت مجانية",
                iconTint = FutGold,
                onClick = {
                    onOpenPackReveal()
                    onCloseDrawer()
                }
            )
            DrawerItem(
                icon = Icons.Default.Help,
                label = "Track Orders History",
                subtitle = "تتبع طلباتي والتحويل",
                onClick = {
                    onNavigate(NavTab.ACCOUNT)
                    onCloseDrawer()
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Divider(color = SurfaceDarkBorder)

        Spacer(modifier = Modifier.height(14.dp))

        // WhatsApp direct support button in drawer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(WhatsAppGreen)
                .clickable {
                    val url = "https://wa.me/$WHATSAPP_NUMBER?text=Hello%20PRINSS%20FUT%20Support!"
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {}
                    onCloseDrawer()
                }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.SupportAgent,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "WhatsApp Support 24/7",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "PRINSS FUT v2.7.0 • Official Store",
            color = TextMuted,
            fontSize = 10.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    subtitle: String,
    iconTint: Color = FutCyan,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 10.sp
            )
        }
    }
}
