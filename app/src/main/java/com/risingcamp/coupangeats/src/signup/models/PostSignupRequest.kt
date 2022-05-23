package com.risingcamp.coupangeats.src.signup.models

import com.google.gson.annotations.SerializedName

data class PostSignupRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("agreements") val agreements: List<Boolean>
)