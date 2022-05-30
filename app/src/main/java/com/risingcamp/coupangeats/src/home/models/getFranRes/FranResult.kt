package com.risingcamp.coupangeats.src.home.models.getFranRes

data class FranResult(
    val deliveryTime: Int,
    val distance: Double,
    val minDeliveryFee: Int,
    val resImageUrl: String,
    val resName: String,
    val restaurantId: Int,
    val reviewCount: Int,
    val starPoint: Double
)