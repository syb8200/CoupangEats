package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.signup.models.PostSignupRequest
import com.risingcamp.coupangeats.src.signup.models.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService(val signupInterface: SignupInterface) {

    fun tryPostSignup(postSignupRequest: PostSignupRequest){
        val signupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)
        signupRetrofitInterface.postSignup(postSignupRequest).enqueue(object :
            Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                signupInterface.onPostSignupSuccess(response.body() as SignupResponse)
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                signupInterface.onPostSignupFailure(t.message ?: "통신 오류")
            }

        })
    }
}