package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.home.models.getNewRes.GetNewResResponse
import com.risingcamp.coupangeats.src.home.models.getResList.GetResListResponse
import com.risingcamp.coupangeats.src.home.models.getTopBanner.GetTopBannerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeRetrofitInterface {

    @GET("app/categories")
    fun getCategory() : Call<GetCategoryResponse>

    @GET("app/users/{userId}/addresses")
    fun getLocation(
        @Path("userId", encoded = true) userId : Int,
        @Query("isSelected") isSelected : Boolean
    ): Call<GetLocationResponse>

    @GET("app/restaurants/franchise")
    fun getFranRes() : Call<GetFranResResponse>

    @GET("app/restaurants/new")
    fun getNewRes() : Call<GetNewResResponse>

    @GET("app/events/top")
    fun getTopBanner() : Call<GetTopBannerResponse>

    @GET("app/restaurants")
    fun getResList(
        @Query("categoryId") categoryId : Int
    ) : Call<GetResListResponse>
}