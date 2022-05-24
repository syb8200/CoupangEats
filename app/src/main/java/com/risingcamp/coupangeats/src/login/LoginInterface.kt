package com.risingcamp.coupangeats.src.login

import com.risingcamp.coupangeats.src.login.models.LoginResponse
import com.risingcamp.coupangeats.src.signup.models.postSignup.SignupResponse

interface LoginInterface {

    fun onPostLoginSuccess(response: LoginResponse)

    fun onPostLoginFailure(message: String)

}