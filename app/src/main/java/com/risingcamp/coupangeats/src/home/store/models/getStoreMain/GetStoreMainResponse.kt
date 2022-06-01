package com.risingcamp.coupangeats.src.home.store.models.getStoreMain

data class GetStoreMainResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)