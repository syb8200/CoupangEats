package com.risingcamp.coupangeats.src.home.store.models.getStoreMain

data class Result(
    val deliveryTime: Int,
    val isCheetah: Int,
    val minDeliveryFee: Int,
    val minOrderPrice: Int,
    val resImageUrlList: List<String>,
    val resName: String,
    val restaurantId: Int,
    val reviewCount: Int,
    val starPoint: Double
)