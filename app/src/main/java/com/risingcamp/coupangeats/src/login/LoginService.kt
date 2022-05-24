package com.risingcamp.coupangeats.src.login

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.login.models.LoginResponse
import com.risingcamp.coupangeats.src.login.models.PostLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginInterface: LoginInterface) {

    fun tryPostLogin(postLoginRequest: PostLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                loginInterface.onPostLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginInterface.onPostLoginFailure(t.message ?: "통신 오류")
            }

        })
    }
}