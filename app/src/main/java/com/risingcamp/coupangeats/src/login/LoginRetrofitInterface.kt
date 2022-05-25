package com.risingcamp.coupangeats.src.login

import com.risingcamp.coupangeats.src.login.models.LoginResponse
import com.risingcamp.coupangeats.src.login.models.PostLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("app/users/sign-in")
    fun postLogin(@Body params: PostLoginRequest) : Call<LoginResponse>
}