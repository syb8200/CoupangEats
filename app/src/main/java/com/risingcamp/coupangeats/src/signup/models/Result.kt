package com.risingcamp.coupangeats.src.signup.models

data class Result(
    val jwt: String,
    val refreshJwt: String,
    val userId: Int
)