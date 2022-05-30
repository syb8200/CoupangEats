package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.home.models.getNewRes.GetNewResResponse

interface HomeInterface {

    fun onGetCategorySuccess(getCategoryResponse: GetCategoryResponse)

    fun onGetCategoryFailure(message: String)

    fun onGetLocationSuccess(getLocationResponse: GetLocationResponse)

    fun onGetLocationFailure(message: String)

    fun onGetFranResSuccess(getFranResResponse: GetFranResResponse)

    fun onGetFranResFailure(message: String)

    fun onGetNewResSuccess(getNewResResponse: GetNewResResponse)

    fun onGetNewResFailure(message: String)
}