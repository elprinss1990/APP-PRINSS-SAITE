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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.OrderItem
import com.example.model.OrderStatus
import com.example.ui.theme.FutCyan
import com.example.ui.theme.FutGold
import com.example.ui.theme.FutGreen
import com.example.ui.theme.FutPurple
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.SurfaceDarkContainer
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrdersScreen(
    viewModel: MainViewModel,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orders: List<OrderItem> by viewModel.orders.collectAsStateWithLifecycle()
    var selectedFilter by remember { mutableStateOf(OrderStatus.ALL) }

    val filteredOrders: List<OrderItem> = remember(orders, selectedFilter) {
        if (selectedFilter == OrderStatus.ALL) orders
        else orders.filter { it.status == selectedFilter }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header: Menu | My Orders / Track your orders | Search
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark1)
                        .border(1.dp, SurfaceDarkBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "My Orders",
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "Track your orders",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark1)
                        .border(1.dp, SurfaceDarkBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextSecondary
                    )
                }
            }
        }

        // Status Filter Tabs: [All] [Processing] [Completed] [Cancelled]
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderStatus.entries.forEach { status ->
                    val isSelected = selectedFilter == status
                    val filterBrush = if (isSelected) {
                        Brush.horizontalGradient(listOf(FutPurple, FutCyan))
                    } else {
                        Brush.linearGradient(listOf(SurfaceDark1, SurfaceDark1))
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(filterBrush)
                            .border(
                                1.dp,
                                if (isSelected) FutCyan else SurfaceDarkBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedFilter = status }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = status.title,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // List of Orders
        if (filteredOrders.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No orders found in this filter",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            items(filteredOrders, key = { it.id }) { order ->
                OrderCard(
                    order = order,
                    onViewDetails = { viewModel.openOrderDetails(order) }
                )
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: OrderItem,
    onViewDetails: () -> Unit
) {
    val numFormat = NumberFormat.getNumberInstance(Locale.US)

    val statusColor = when (order.status) {
        OrderStatus.COMPLETED -> FutGreen
        OrderStatus.PROCESSING -> FutPurple
        OrderStatus.CANCELLED -> Color(0xFFEF4444)
        OrderStatus.ALL -> FutCyan
    }

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
                .padding(14.dp)
        ) {
            // Header: #PF25896 and Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.orderNumber,
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
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
                        fontSize = 11.sp
                    )
                }
            }

            Text(
                text = order.dateText,
                color = TextMuted,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            )

            // Order Content Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // UT Gold badge
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(FutGold.copy(alpha = 0.15f))
                        .border(1.5.dp, FutGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "UT",
                        color = FutGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = order.title,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = "${numFormat.format(order.totalEgp.toInt())} EGP",
                    color = FutCyan,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // View Details Button with subtle border
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceDarkContainer)
                    .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(10.dp))
                    .clickable { onViewDetails() }
                    .padding(vertical = 8.dp)
                    .testTag("order_view_details_${order.orderNumber}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "View Details",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
