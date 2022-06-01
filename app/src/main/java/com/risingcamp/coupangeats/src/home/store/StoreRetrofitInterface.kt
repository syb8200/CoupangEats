package com.risingcamp.coupangeats.src.home.store

import com.risingcamp.coupangeats.src.home.store.models.getStoreCategory.GetStoreCategoryResponse
import com.risingcamp.coupangeats.src.home.store.models.getStoreMain.GetStoreMainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreRetrofitInterface {

    @GET("app/restaurants/{restaurantId}")
    fun getStoreMain(
        @Path("restaurantId", encoded = true) restaurantId : Int
    ): Call<GetStoreMainResponse>

    @GET("app/restaurants/{restaurantId}/kinds")
    fun getStoreCategory(
        @Path("restaurantId", encoded = true) restaurantId : Int
    ): Call<GetStoreCategoryResponse>
}