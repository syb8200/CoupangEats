package com.risingcamp.coupangeats.src

import com.risingcamp.coupangeats.src.models.GetAutoLoginResponse

interface AutoLoginInterface {

    fun onGetAutoLoginSuccess(getAutoLoginResponse: GetAutoLoginResponse)

    fun onGetAutoLoginFailure(message: String)
}