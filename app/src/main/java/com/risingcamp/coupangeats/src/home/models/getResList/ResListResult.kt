package com.risingcamp.coupangeats.src.home.models.getResList

data class ResListResult(
    val deliveryTime: Int,
    val distance: Double,
    val isCheetah: Int,
    val minDeliveryFee: Int,
    val resImageUrlList: List<String>,
    val resName: String,
    val restaurantId: Int,
    val reviewCount: Int,
    val starPoint: Double
)