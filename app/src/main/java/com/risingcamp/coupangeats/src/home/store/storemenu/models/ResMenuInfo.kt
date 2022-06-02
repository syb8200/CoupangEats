package com.risingcamp.coupangeats.src.home.store.storemenu.models

data class ResMenuInfo(
    val menuDescription: String,
    val menuId: Int,
    val menuImageUrlList: List<String>,
    val menuName: String,
    val menuPrice: Int
)