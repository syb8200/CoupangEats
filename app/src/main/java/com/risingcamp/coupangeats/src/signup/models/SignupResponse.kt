package com.risingcamp.coupangeats.src.signup.models

data class SignupResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)