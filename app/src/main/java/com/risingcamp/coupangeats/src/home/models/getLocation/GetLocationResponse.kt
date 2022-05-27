package com.risingcamp.coupangeats.src.home.models.getLocation

data class GetLocationResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)