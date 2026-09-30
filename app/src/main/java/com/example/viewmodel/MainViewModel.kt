package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CartItem
import com.example.model.OrderItem
import com.example.model.OrderStatus
import com.example.model.Platform
import com.example.ui.components.NavTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class MainViewModel : ViewModel() {

    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    private val _selectedPlatform = MutableStateFlow(Platform.PLAYSTATION)
    val selectedPlatform: StateFlow<Platform> = _selectedPlatform.asStateFlow()

    // Coins Calculator State (defaults to 1.5M as in Screen 2)
    private val _coinsQuantity = MutableStateFlow(1500000L)
    val coinsQuantity: StateFlow<Long> = _coinsQuantity.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(
                id = "c1",
                title = "EA FC 27 Coins (PS)",
                subtitle = "1,500,000 Coins",
                platform = Platform.PLAYSTATION,
                coinAmount = 1500000L,
                quantity = 1,
                unitPriceEgp = 1920.0,
                isCoinProduct = true
            ),
            CartItem(
                id = "c2",
                title = "EA FC 27 Coins (PC)",
                subtitle = "500,000 Coins",
                platform = Platform.PC,
                coinAmount = 500000L,
                quantity = 1,
                unitPriceEgp = 640.0,
                isCoinProduct = true
            )
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _promoCode = MutableStateFlow("")
    val promoCode: StateFlow<String> = _promoCode.asStateFlow()

    private val _appliedDiscountPercent = MutableStateFlow(0)
    val appliedDiscountPercent: StateFlow<Int> = _appliedDiscountPercent.asStateFlow()

    // Orders list matching Screen 4 of the user's mockup
    private val _orders = MutableStateFlow<List<OrderItem>>(
        listOf(
            OrderItem(
                id = "o1",
                orderNumber = "#PF25896",
                dateText = "12 Sep 2025 - 14:32",
                title = "EA FC 27 Coins (PS) 1,000,000 Coins",
                platform = Platform.PLAYSTATION,
                coinsAmount = 1000000L,
                totalEgp = 1280.0,
                status = OrderStatus.COMPLETED
            ),
            OrderItem(
                id = "o2",
                orderNumber = "#PF25895",
                dateText = "11 Sep 2025 - 18:20",
                title = "EA FC 27 Coins (PC) 2,000,000 Coins",
                platform = Platform.PC,
                coinsAmount = 2000000L,
                totalEgp = 2560.0,
                status = OrderStatus.PROCESSING
            ),
            OrderItem(
                id = "o3",
                orderNumber = "#PF25894",
                dateText = "10 Sep 2025 - 12:15",
                title = "EA FC 27 Coins (PS) 500,000 Coins",
                platform = Platform.PLAYSTATION,
                coinsAmount = 500000L,
                totalEgp = 640.0,
                status = OrderStatus.COMPLETED
            ),
            OrderItem(
                id = "o4",
                orderNumber = "#PF25893",
                dateText = "09 Sep 2025 - 16:10",
                title = "EA FC 27 Coins (PC) 1,500,000 Coins",
                platform = Platform.PC,
                coinsAmount = 1500000L,
                totalEgp = 1920.0,
                status = OrderStatus.CANCELLED
            )
        )
    )
    val orders: StateFlow<List<OrderItem>> = _orders.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isPackRevealOpen = MutableStateFlow(false)
    val isPackRevealOpen: StateFlow<Boolean> = _isPackRevealOpen.asStateFlow()

    private val _selectedOrderForDetails = MutableStateFlow<OrderItem?>(null)
    val selectedOrderForDetails: StateFlow<OrderItem?> = _selectedOrderForDetails.asStateFlow()

    private val _isCheckoutOpen = MutableStateFlow(false)
    val isCheckoutOpen: StateFlow<Boolean> = _isCheckoutOpen.asStateFlow()

    fun selectTab(tab: NavTab) {
        _currentTab.value = tab
    }

    fun selectPlatform(platform: Platform) {
        _selectedPlatform.value = platform
    }

    fun setCoinsQuantity(quantity: Long) {
        _coinsQuantity.value = quantity
    }

    fun getPricePerMillion(platform: Platform): Double {
        return when (platform) {
            Platform.PLAYSTATION -> 1280.0
            Platform.PC -> 1280.0 // matching screenshot exact 1,280 EGP per 1 Million
            Platform.XBOX -> 1350.0
        }
    }

    fun calculateCoinPrice(quantity: Long, platform: Platform): Double {
        val rate = getPricePerMillion(platform)
        return (quantity.toDouble() / 1_000_000.0) * rate
    }

    fun addCoinsToCart(quantity: Long, platform: Platform) {
        val price = calculateCoinPrice(quantity, platform)
        val formattedCoins = String.format(Locale.US, "%,d", quantity)
        val newItem = CartItem(
            id = UUID.randomUUID().toString(),
            title = "EA FC 27 Coins (${platform.shortName})",
            subtitle = "$formattedCoins Coins",
            platform = platform,
            coinAmount = quantity,
            quantity = 1,
            unitPriceEgp = price,
            isCoinProduct = true
        )
        _cartItems.update { current ->
            current + newItem
        }
    }

    fun addServiceToCart(title: String, priceEgp: Double, platform: Platform) {
        val newItem = CartItem(
            id = UUID.randomUUID().toString(),
            title = title,
            subtitle = platform.displayName,
            platform = platform,
            coinAmount = 0L,
            quantity = 1,
            unitPriceEgp = priceEgp,
            isCoinProduct = false
        )
        _cartItems.update { it + newItem }
    }

    fun incrementCartItem(itemId: String) {
        _cartItems.update { list ->
            list.map {
                if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it
            }
        }
    }

    fun decrementCartItem(itemId: String) {
        _cartItems.update { list ->
            list.mapNotNull {
                if (it.id == itemId) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                } else it
            }
        }
    }

    fun removeCartItem(itemId: String) {
        _cartItems.update { list ->
            list.filterNot { it.id == itemId }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun applyPromoCode(code: String): Boolean {
        _promoCode.value = code.trim().uppercase()
        return if (code.trim().equals("PRINSS27", ignoreCase = true) ||
            code.trim().equals("FUT27", ignoreCase = true) ||
            code.trim().equals("VIP", ignoreCase = true)
        ) {
            _appliedDiscountPercent.value = 10
            true
        } else {
            _appliedDiscountPercent.value = 0
            false
        }
    }

    fun openPackReveal() {
        _isPackRevealOpen.value = true
    }

    fun closePackReveal() {
        _isPackRevealOpen.value = false
    }

    fun openOrderDetails(order: OrderItem) {
        _selectedOrderForDetails.value = order
    }

    fun closeOrderDetails() {
        _selectedOrderForDetails.value = null
    }

    fun openCheckout() {
        _isCheckoutOpen.value = true
    }

    fun closeCheckout() {
        _isCheckoutOpen.value = false
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun createOrder(transferMethod: String, eaEmail: String, paymentMethod: String) {
        val items = _cartItems.value
        if (items.isEmpty()) return

        val total = items.sumOf { it.totalPriceEgp }
        val discount = total * (_appliedDiscountPercent.value / 100.0)
        val finalTotal = total - discount
        val totalCoins = items.filter { it.isCoinProduct }.sumOf { it.coinAmount * it.quantity }
        val primaryPlatform = items.firstOrNull()?.platform ?: Platform.PLAYSTATION

        val randId = (25897..26999).random()
        val dateFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.US)
        val newOrder = OrderItem(
            id = UUID.randomUUID().toString(),
            orderNumber = "#PF$randId",
            dateText = dateFormat.format(Date()),
            title = if (totalCoins > 0) "EA FC 27 Coins (${primaryPlatform.shortName}) ${String.format(Locale.US, "%,d", totalCoins)} Coins" else items.first().title,
            platform = primaryPlatform,
            coinsAmount = totalCoins,
            totalEgp = finalTotal,
            status = OrderStatus.PROCESSING,
            transferMethod = transferMethod
        )

        _orders.update { listOf(newOrder) + it }
        clearCart()
        _currentTab.value = NavTab.ACCOUNT
    }
}
