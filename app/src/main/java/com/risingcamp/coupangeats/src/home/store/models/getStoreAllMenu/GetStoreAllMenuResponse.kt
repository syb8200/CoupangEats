package com.risingcamp.coupangeats.src.home.store.models.getStoreAllMenu

data class GetStoreAllMenuResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)