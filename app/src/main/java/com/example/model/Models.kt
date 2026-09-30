package com.example.model

enum class Platform(val displayName: String, val shortName: String) {
    PLAYSTATION("PlayStation", "PS"),
    PC("PC (EA App)", "PC"),
    XBOX("Xbox", "Xbox")
}

enum class Currency(val symbol: String, val code: String, val rateToEgp: Double) {
    EGP("EGP", "EGP", 1.0),
    SAR("SAR", "SAR", 0.08),
    USD("$", "USD", 0.021),
    AED("AED", "AED", 0.077)
}

enum class OrderStatus(val title: String) {
    ALL("All"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

data class CartItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val platform: Platform,
    val coinAmount: Long = 0,
    val quantity: Int = 1,
    val unitPriceEgp: Double,
    val isCoinProduct: Boolean = true
) {
    val totalPriceEgp: Double get() = unitPriceEgp * quantity
}

data class OrderItem(
    val id: String,
    val orderNumber: String,
    val dateText: String,
    val title: String,
    val platform: Platform,
    val coinsAmount: Long,
    val totalEgp: Double,
    val status: OrderStatus,
    val transferMethod: String = "Comfort Trade"
)

data class ServiceItem(
    val id: String,
    val titleEn: String,
    val titleAr: String,
    val iconType: String,
    val badge: String? = null,
    val startingPriceEgp: Double
)

data class GamingCardItem(
    val id: String,
    val title: String,
    val platform: Platform,
    val denomination: String,
    val priceEgp: Double,
    val iconType: String
)

data class PackPlayer(
    val name: String,
    val rating: Int,
    val position: String,
    val club: String,
    val nation: String,
    val pace: Int,
    val shooting: Int,
    val passing: Int,
    val dribbling: Int,
    val defending: Int,
    val physical: Int,
    val cardType: String = "TOTY"
)
