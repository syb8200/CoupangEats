package com.risingcamp.coupangeats.src.home.store.storemenu

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.home.store.storemenu.models.GetStoreMenuOptionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreMenuService(val storeMenuInterface: StoreMenuInterface) {

    fun tryGetStoreMenuOption(restaurantId : Int, menuId : Int){
        val storeMenuRetrofitInterface = ApplicationClass.sRetrofit.create(StoreMenuRetrofitInterface::class.java)
        storeMenuRetrofitInterface.getStoreMenuOption(restaurantId, menuId).enqueue(object :
            Callback<GetStoreMenuOptionResponse> {
            override fun onResponse(
                call: Call<GetStoreMenuOptionResponse>,
                response: Response<GetStoreMenuOptionResponse>
            ) {
                storeMenuInterface.onGetStoreMenuOptionSuccess(response.body() as GetStoreMenuOptionResponse)
            }

            override fun onFailure(call: Call<GetStoreMenuOptionResponse>, t: Throwable) {
                storeMenuInterface.onGetStoreMenuOptionFailure(t.message ?: "통신 오류")
            }

        })
    }
}