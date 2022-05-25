package com.risingcamp.coupangeats.src.signup.models.GetUsersPhone

data class GetUsersPhoneResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)