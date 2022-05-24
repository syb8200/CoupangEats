package com.risingcamp.coupangeats.src.signup.models.getUsersEmail

data class GetUsersEmailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)