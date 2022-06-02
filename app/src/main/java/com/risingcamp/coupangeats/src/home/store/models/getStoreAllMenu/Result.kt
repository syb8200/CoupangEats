package com.risingcamp.coupangeats.src.home.store.models.getStoreAllMenu

data class Result(
    val kindId: Int,
    val kindName: String,
    val menuDescription: String,
    val menuId: Int,
    val menuImageUrl: Any,
    val menuName: String,
    val menuPrice: Int
)