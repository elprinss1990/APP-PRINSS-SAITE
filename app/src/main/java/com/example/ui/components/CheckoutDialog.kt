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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.CartItem
import com.example.ui.theme.FutCyan
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
fun CheckoutDialog(
    cartItems: List<CartItem>,
    totalEgp: Double,
    onDismiss: () -> Unit,
    onOrderPlaced: (transferMethod: String, eaEmail: String, paymentMethod: String) -> Unit
) {
    val context = LocalContext.current
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    var selectedMethod by remember { mutableStateOf("Comfort Trade") }
    var selectedPayment by remember { mutableStateOf("InstaPay / Vodafone Cash") }
    var eaEmail by remember { mutableStateOf("") }
    var gamerTag by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Secure Checkout",
                            tint = FutCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Secure Checkout",
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
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

                Spacer(modifier = Modifier.height(14.dp))

                // Order amount badge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceDarkContainer)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${cartItems.size} items in order",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "${numFormat.format(totalEgp.toInt())} EGP",
                            color = FutGreen,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delivery Method
                Text(
                    text = "Transfer Method / طريقة التسليم",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val methods = listOf("Comfort Trade", "Player Auction")
                    methods.forEach { method ->
                        val isSelected = selectedMethod == method
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) FutCyan.copy(alpha = 0.15f) else SurfaceDarkContainer)
                                .border(
                                    1.5.dp,
                                    if (isSelected) FutCyan else SurfaceDarkBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedMethod = method }
                                .padding(vertical = 10.dp, horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = method,
                                    color = if (isSelected) FutCyan else TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = if (method == "Comfort Trade") "Fast & Auto (مريح)" else "Manual Auction (مزاد)",
                                    color = TextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Account Inputs
                Text(
                    text = "EA Account Details (Encrypted 256-bit)",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = eaEmail,
                    onValueChange = { eaEmail = it },
                    label = { Text("EA WebApp Email or PSN / Xbox ID") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FutCyan,
                        unfocusedBorderColor = SurfaceDarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedLabelColor = FutCyan,
                        unfocusedLabelColor = TextSecondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_email_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Method
                Text(
                    text = "Select Payment Method",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                val paymentOptions = listOf(
                    "InstaPay / Vodafone Cash",
                    "Credit / Debit Card (Visa / MC)",
                    "Crypto (USDT TRC20)",
                    "PayPal"
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    paymentOptions.forEach { opt ->
                        val isSelected = selectedPayment == opt
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) FutPurple.copy(alpha = 0.15f) else SurfaceDarkContainer)
                                .border(
                                    1.dp,
                                    if (isSelected) FutPurple else SurfaceDarkBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedPayment = opt }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) FutPurple else Color.Transparent)
                                    .border(1.5.dp, if (isSelected) FutPurple else TextMuted, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = opt,
                                color = if (isSelected) TextPrimary else TextSecondary,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Place Order: the app has no payment backend, so the order is sent to the
                // store on WhatsApp and recorded locally as "Processing".
                Button(
                    onClick = {
                        val msg = "Hello PRINSS FUT! I would like to place an order for:\n" +
                                cartItems.joinToString("\n") { "• ${it.title} - ${it.totalPriceEgp.toInt()} EGP" } +
                                "\nTotal: ${numFormat.format(totalEgp.toInt())} EGP" +
                                "\nTransfer method: $selectedMethod" +
                                "\nPayment method: $selectedPayment" +
                                (if (eaEmail.isNotBlank()) "\nEA email: $eaEmail" else "")
                        val url = "https://wa.me/$WHATSAPP_NUMBER?text=${Uri.encode(msg)}"
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            onOrderPlaced(selectedMethod, eaEmail.trim(), selectedPayment)
                            onDismiss()
                        } catch (e: Exception) {}
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("checkout_confirm_order_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Send Order via WhatsApp (${numFormat.format(totalEgp.toInt())} EGP)",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
