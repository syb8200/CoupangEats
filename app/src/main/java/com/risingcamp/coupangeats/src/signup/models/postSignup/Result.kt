package com.risingcamp.coupangeats.src.signup.models.postSignup

data class Result(
    val jwt: String,
    val refreshJwt: String,
    val userId: Int
)