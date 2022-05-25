package com.risingcamp.coupangeats.src.login.models

data class Result(
    val jwt: String,
    val refreshJwt: String,
    val userId: Int
)
