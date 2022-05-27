package com.risingcamp.coupangeats.src.models

data class GetAutoLoginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)