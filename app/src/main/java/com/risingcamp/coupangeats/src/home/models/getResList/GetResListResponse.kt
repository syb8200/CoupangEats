package com.risingcamp.coupangeats.src.home.models.getResList

data class GetResListResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<ResListResult>
)