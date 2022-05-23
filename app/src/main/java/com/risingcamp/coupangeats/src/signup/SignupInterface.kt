package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.src.signup.models.SignupResponse

interface SignupInterface {

    fun onPostSignupSuccess(response: SignupResponse)

    fun onPostSignupFailure(message: String)


}