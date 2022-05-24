package com.risingcamp.coupangeats.src.login.models

import com.google.gson.annotations.SerializedName

data class PostLoginRequest (
    //변경필요
    @SerializedName("email") val email: String
)