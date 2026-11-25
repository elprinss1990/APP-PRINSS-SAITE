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

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _promoCode = MutableStateFlow("")
    val promoCode: StateFlow<String> = _promoCode.asStateFlow()

    private val _appliedDiscountPercent = MutableStateFlow(0)
    val appliedDiscountPercent: StateFlow<Int> = _appliedDiscountPercent.asStateFlow()

    private val _orders = MutableStateFlow<List<OrderItem>>(emptyList())
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
