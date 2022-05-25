package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.src.signup.models.GetUsersPhone.GetUsersPhoneResponse
import com.risingcamp.coupangeats.src.signup.models.getUsersEmail.GetUsersEmailResponse
import com.risingcamp.coupangeats.src.signup.models.postSignup.PostSignupRequest
import com.risingcamp.coupangeats.src.signup.models.postSignup.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignupRetrofitInterface {

    @POST("app/users/sign-up")
    fun postSignup(@Body params: PostSignupRequest) : Call<SignupResponse>

    @GET("app/users")
    fun getUsersEmail(@Query("email") email : String
    ) : Call<GetUsersEmailResponse>

    @GET("app/users")
    fun getUsersPhone(@Query("phone") phone : String
    ) : Call<GetUsersPhoneResponse>

}