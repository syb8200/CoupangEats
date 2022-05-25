package com.risingcamp.coupangeats.src.login.models

import com.google.gson.annotations.SerializedName

data class PostLoginRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)