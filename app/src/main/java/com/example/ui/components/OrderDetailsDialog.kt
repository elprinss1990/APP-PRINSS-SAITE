package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.OrderItem
import com.example.model.OrderStatus
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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderDetailsDialog(
    order: OrderItem,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    val progressValue = when (order.status) {
        OrderStatus.COMPLETED -> 1.0f
        OrderStatus.PROCESSING -> 0.65f
        OrderStatus.CANCELLED -> 0.0f
        OrderStatus.ALL -> 0.5f
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark1),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = order.orderNumber,
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                        Text(
                            text = order.dateText,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SurfaceDarkContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Status Badge & Progress
                val statusColor = when (order.status) {
                    OrderStatus.COMPLETED -> FutGreen
                    OrderStatus.PROCESSING -> FutPurple
                    OrderStatus.CANCELLED -> Color(0xFFEF4444)
                    else -> FutCyan
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Transfer Status:",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(statusColor.copy(alpha = 0.15f))
                            .border(1.dp, statusColor, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = order.status.title,
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { progressValue },
                    color = statusColor,
                    trackColor = SurfaceDarkContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Steps list
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    StepRow(
                        title = "Order & Payment Verified",
                        subtitle = "Instant authorization received",
                        isDone = true
                    )
                    StepRow(
                        title = "Comfort Trade Transfer Agent",
                        subtitle = "Connected to secure transfer server",
                        isDone = order.status != OrderStatus.CANCELLED
                    )
                    StepRow(
                        title = "Coins Transferred to Account",
                        subtitle = "${numFormat.format(order.coinsAmount)} Coins transferred",
                        isDone = order.status == OrderStatus.COMPLETED
                    )
                    StepRow(
                        title = "Account Safety & Delivery Completed",
                        subtitle = "100% Ban-Free Comfort Trade guarantee",
                        isDone = order.status == OrderStatus.COMPLETED
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Order summary details
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceDarkContainer)
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Item:", color = TextSecondary, fontSize = 13.sp)
                            Text(order.title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Platform:", color = TextSecondary, fontSize = 13.sp)
                            Text(order.platform.displayName, color = FutCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Method:", color = TextSecondary, fontSize = 13.sp)
                            Text(order.transferMethod, color = TextPrimary, fontSize = 13.sp)
                        }
                        Divider(
                            color = SurfaceDarkBorder,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid:", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(
                                "${numFormat.format(order.totalEgp.toInt())} EGP",
                                color = FutGreen,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // WhatsApp Support button
                Button(
                    onClick = {
                        val message = "Hello PRINSS FUT Support, I need an update on my order ${order.orderNumber} for ${order.title} (${order.platform.displayName})."
                        val url = "https://wa.me/201000000000?text=${Uri.encode(message)}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Handled gracefully
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("order_support_whatsapp_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SupportAgent,
                        contentDescription = "Support",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Contact Support on WhatsApp",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StepRow(
    title: String,
    subtitle: String,
    isDone: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isDone) FutGreen else SurfaceDarkContainer)
                .border(
                    width = 1.dp,
                    color = if (isDone) FutGreen else SurfaceDarkBorder,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = SurfaceDarkVoid,
                    modifier = Modifier.size(14.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(TextMuted)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                color = if (isDone) TextPrimary else TextMuted,
                fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 13.sp
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
    }
}
