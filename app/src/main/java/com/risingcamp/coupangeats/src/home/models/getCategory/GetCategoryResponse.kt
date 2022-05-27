package com.risingcamp.coupangeats.src.home.models.getCategory

data class GetCategoryResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)