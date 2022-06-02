package com.risingcamp.coupangeats.src.home.store.storemenu.models

data class GetStoreMenuOptionResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)