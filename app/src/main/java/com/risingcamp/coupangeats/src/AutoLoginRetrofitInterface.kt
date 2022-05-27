package com.risingcamp.coupangeats.src

import com.risingcamp.coupangeats.src.models.GetAutoLoginResponse
import retrofit2.Call
import retrofit2.http.GET

interface AutoLoginRetrofitInterface {

    @GET("app/users/sign-in/jwt")
    fun getAutoLogin() : Call<GetAutoLoginResponse>
}