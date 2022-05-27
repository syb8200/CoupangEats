package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse

interface HomeInterface {

    fun onGetCategorySuccess(getCategoryResponse: GetCategoryResponse)

    fun onGetCategoryFailure(message: String)

    fun onGetLocationSuccess(getLocationResponse: GetLocationResponse)

    fun onGetLocationFailure(message: String)
}