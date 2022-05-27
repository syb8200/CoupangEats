package com.risingcamp.coupangeats.src

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.models.GetAutoLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutoLoginService(val autoLoginInterface: AutoLoginInterface) {

    fun tryPostAutoLogin(){
        val autoLoginRetrofitInterface = ApplicationClass.sRetrofit.create(AutoLoginRetrofitInterface::class.java)
        autoLoginRetrofitInterface.getAutoLogin().enqueue(object :
            Callback<GetAutoLoginResponse> {
            override fun onResponse(
                call: Call<GetAutoLoginResponse>,
                response: Response<GetAutoLoginResponse>
            ) {
                autoLoginInterface.onGetAutoLoginSuccess(response.body() as GetAutoLoginResponse)
            }

            override fun onFailure(call: Call<GetAutoLoginResponse>, t: Throwable) {
                autoLoginInterface.onGetAutoLoginFailure(t.message ?: "통신 오류")
            }

        })
    }
}