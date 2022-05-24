package com.risingcamp.coupangeats.src.signup

import com.risingcamp.coupangeats.src.signup.models.getUsersEmail.GetUsersEmailResponse
import com.risingcamp.coupangeats.src.signup.models.postSignup.SignupResponse

interface SignupInterface {

    fun onPostSignupSuccess(response: SignupResponse)

    fun onPostSignupFailure(message: String)

    fun onGetUsersEmailSuccess(emailResponse: GetUsersEmailResponse)

    fun onGetUsersEmailFailure(message: String)

}