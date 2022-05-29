package com.risingcamp.coupangeats.src.home.models.getFranRes

data class GetFranResResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)