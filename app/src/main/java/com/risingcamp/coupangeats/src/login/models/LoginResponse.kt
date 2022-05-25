package com.risingcamp.coupangeats.src.login.models

import com.risingcamp.coupangeats.src.signup.models.postSignup.Result

data class LoginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)
