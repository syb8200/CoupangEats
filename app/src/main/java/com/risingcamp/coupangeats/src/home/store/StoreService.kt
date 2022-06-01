package com.risingcamp.coupangeats.src.home.store

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.home.store.models.getStoreCategory.GetStoreCategoryResponse
import com.risingcamp.coupangeats.src.home.store.models.getStoreMain.GetStoreMainResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreService(val storeInterface: StoreInterface) {

    fun tryGetStoreMain(restaurantId : Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStoreMain(restaurantId).enqueue(object :
            Callback<GetStoreMainResponse> {
            override fun onResponse(
                call: Call<GetStoreMainResponse>,
                response: Response<GetStoreMainResponse>
            ) {
                storeInterface.onGetStoreMainSuccess(response.body() as GetStoreMainResponse)
            }

            override fun onFailure(call: Call<GetStoreMainResponse>, t: Throwable) {
                storeInterface.onGetStoreMainFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetStoreCategory(restaurantId : Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStoreCategory(restaurantId).enqueue(object :
            Callback<GetStoreCategoryResponse> {
            override fun onResponse(
                call: Call<GetStoreCategoryResponse>,
                response: Response<GetStoreCategoryResponse>
            ) {
                storeInterface.onGetStoreCategorySuccess(response.body() as GetStoreCategoryResponse)
            }

            override fun onFailure(call: Call<GetStoreCategoryResponse>, t: Throwable) {
                storeInterface.onGetStoreCategoryFailure(t.message ?: "통신 오류")
            }

        })
    }
}