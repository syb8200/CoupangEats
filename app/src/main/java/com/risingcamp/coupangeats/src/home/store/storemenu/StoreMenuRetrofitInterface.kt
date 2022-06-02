package com.risingcamp.coupangeats.src.home.store.storemenu

import com.risingcamp.coupangeats.src.home.store.storemenu.models.GetStoreMenuOptionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreMenuRetrofitInterface {

    @GET("app/restaurants/{restaurantId}/menus/{menuId}")
    fun getStoreMenuOption(
        @Path("restaurantId", encoded = true) restaurantId : Int,
        @Path("menuId", encoded = true) menuId : Int
    ): Call<GetStoreMenuOptionResponse>
}