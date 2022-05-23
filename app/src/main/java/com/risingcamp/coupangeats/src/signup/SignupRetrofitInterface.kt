package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.src.signup.models.PostSignupRequest
import com.risingcamp.coupangeats.src.signup.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupRetrofitInterface {

    @POST("app/users/sign-up")
    fun postSignup(@Body params: PostSignupRequest) : Call<SignupResponse>


}