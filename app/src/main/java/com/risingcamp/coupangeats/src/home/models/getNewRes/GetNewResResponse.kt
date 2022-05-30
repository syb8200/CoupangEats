package com.risingcamp.coupangeats.src.home.models.getNewRes

data class GetNewResResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)