package com.risingcamp.coupangeats.src.home.store.models.getStoreCategory

data class GetStoreCategoryResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)