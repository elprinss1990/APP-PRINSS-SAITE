package com.example

import com.example.model.WHATSAPP_NUMBER
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AtmosphericArenaBackground
import com.example.ui.components.CheckoutDialog
import com.example.ui.components.FcCardRevealDialog
import com.example.ui.components.NavTab
import com.example.ui.components.OrderDetailsDialog
import com.example.ui.components.PrinssFutBottomNav
import com.example.ui.components.PrinssFutDrawerContent
import com.example.ui.components.PrinssFutHeader
import com.example.ui.screens.BuyCoinsScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.GamingScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.theme.PrinssFutTheme
import com.example.ui.theme.SurfaceDarkVoid
import com.example.ui.theme.WhatsAppGreen
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrinssFutTheme {
                PrinssFutApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PrinssFutApp(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val isPackRevealOpen by viewModel.isPackRevealOpen.collectAsStateWithLifecycle()
    val selectedOrderForDetails by viewModel.selectedOrderForDetails.collectAsStateWithLifecycle()
    val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Handle back button on sub-screens
    BackHandler(enabled = currentTab != NavTab.HOME || drawerState.isOpen) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            viewModel.selectTab(NavTab.HOME)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            PrinssFutDrawerContent(
                onNavigate = { tab ->
                    viewModel.selectTab(tab)
                },
                onOpenPackReveal = {
                    viewModel.openPackReveal()
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        AtmosphericArenaBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    PrinssFutHeader(
                        cartCount = cartItems.sumOf { it.quantity },
                        onMenuClick = {
                            scope.launch { drawerState.open() }
                        },
                        onCartClick = {
                            viewModel.selectTab(NavTab.CART)
                        },
                        onSearchQueryChange = { query ->
                            viewModel.setSearchQuery(query)
                        }
                    )
                },
                bottomBar = {
                    PrinssFutBottomNav(
                        currentTab = currentTab,
                        cartItemCount = cartItems.sumOf { it.quantity },
                        onTabSelected = { tab ->
                            viewModel.selectTab(tab)
                        }
                    )
                },
                floatingActionButton = {
                    val context = LocalContext.current
                    val infiniteTransition = rememberInfiniteTransition(label = "waPulse")
                    val pulseScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.08f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "waPulseScale"
                    )

                    Box(
                        modifier = Modifier
                            .padding(bottom = 64.dp)
                            .size(54.dp)
                            .graphicsLayer {
                                scaleX = pulseScale
                                scaleY = pulseScale
                            }
                            .clip(CircleShape)
                            .background(WhatsAppGreen)
                            .clickable {
                                val uri = Uri.parse("https://wa.me/$WHATSAPP_NUMBER?text=${Uri.encode("مرحباً متجر برنس فوت، أود الاستفسار عن كوينز EA FC 27")}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                try {
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💬", fontSize = 24.sp)
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Crossfade(
                        targetState = currentTab,
                        animationSpec = tween(250),
                        label = "screenTransition"
                    ) { tab ->
                        when (tab) {
                            NavTab.HOME -> {
                                HomeScreen(viewModel = viewModel)
                            }
                            NavTab.EA_FC_27 -> {
                                BuyCoinsScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.selectTab(NavTab.HOME) }
                                )
                            }
                            NavTab.GAMING -> {
                                GamingScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.selectTab(NavTab.HOME) }
                                )
                            }
                            NavTab.CART -> {
                                CartScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.selectTab(NavTab.HOME) }
                                )
                            }
                            NavTab.ACCOUNT -> {
                                OrdersScreen(
                                    viewModel = viewModel,
                                    onMenuClick = { scope.launch { drawerState.open() } }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Dialogs
    if (isPackRevealOpen) {
        FcCardRevealDialog(
            onDismiss = { viewModel.closePackReveal() },
            onClaimReward = { bonusCoins ->
                viewModel.addCoinsToCart(bonusCoins, viewModel.selectedPlatform.value)
            }
        )
    }

    selectedOrderForDetails?.let { order ->
        OrderDetailsDialog(
            order = order,
            onDismiss = { viewModel.closeOrderDetails() }
        )
    }

    if (isCheckoutOpen) {
        val total = cartItems.sumOf { it.totalPriceEgp }
        val discount = total * (viewModel.appliedDiscountPercent.value / 100.0)
        val finalTotal = total - discount
        CheckoutDialog(
            cartItems = cartItems,
            totalEgp = finalTotal,
            onDismiss = { viewModel.closeCheckout() },
            onOrderPlaced = { transferMethod, eaEmail, paymentMethod ->
                viewModel.createOrder(transferMethod, eaEmail, paymentMethod)
            }
        )
    }
}
