package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.signup.models.GetUsersPhone.GetUsersPhoneResponse
import com.risingcamp.coupangeats.src.signup.models.getUsersEmail.GetUsersEmailResponse
import com.risingcamp.coupangeats.src.signup.models.postSignup.PostSignupRequest
import com.risingcamp.coupangeats.src.signup.models.postSignup.SignupResponse
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

    fun tryGetUsersEmail(email : String){
        val signupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)
        signupRetrofitInterface.getUsersEmail(email).enqueue(object : Callback<GetUsersEmailResponse> {
            override fun onResponse(
                call: Call<GetUsersEmailResponse>,
                response: Response<GetUsersEmailResponse>
            ) {
                signupInterface.onGetUsersEmailSuccess(response.body() as GetUsersEmailResponse)
            }

            override fun onFailure(call: Call<GetUsersEmailResponse>, t: Throwable) {
                signupInterface.onGetUsersEmailFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetUsersPhone(phone : String){
        val signupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)
        signupRetrofitInterface.getUsersPhone(phone).enqueue(object : Callback<GetUsersPhoneResponse> {
            override fun onResponse(
                call: Call<GetUsersPhoneResponse>,
                response: Response<GetUsersPhoneResponse>
            ) {
                signupInterface.onGetUsersPhoneSuccess(response.body() as GetUsersPhoneResponse)
            }

            override fun onFailure(call: Call<GetUsersPhoneResponse>, t: Throwable) {
                signupInterface.onGetUsersPhoneFailure(t.message ?: "통신 오류")
            }

        })
    }


}