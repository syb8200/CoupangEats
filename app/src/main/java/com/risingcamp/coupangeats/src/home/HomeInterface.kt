package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.home.models.getNewRes.GetNewResResponse
import com.risingcamp.coupangeats.src.home.models.getResList.GetResListResponse
import com.risingcamp.coupangeats.src.home.models.getTopBanner.GetTopBannerResponse

interface HomeInterface {

    fun onGetCategorySuccess(getCategoryResponse: GetCategoryResponse)

    fun onGetCategoryFailure(message: String)

    fun onGetLocationSuccess(getLocationResponse: GetLocationResponse)

    fun onGetLocationFailure(message: String)

    fun onGetFranResSuccess(getFranResResponse: GetFranResResponse)

    fun onGetFranResFailure(message: String)

    fun onGetNewResSuccess(getNewResResponse: GetNewResResponse)

    fun onGetNewResFailure(message: String)

    fun onGetTopBannerSuccess(getTopBannerResponse: GetTopBannerResponse)

    fun onGetTopBannerFailure(message: String)

    fun onGetResListSuccess(getResListResponse: GetResListResponse)

    fun onGetResListFailure(message: String)
}