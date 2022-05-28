package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val homeInterface: HomeInterface) {


    fun tryGetCategory(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getCategory().enqueue(object :
            Callback<GetCategoryResponse> {
            override fun onResponse(
                call: Call<GetCategoryResponse>,
                response: Response<GetCategoryResponse>
            ) {
                homeInterface.onGetCategorySuccess(response.body() as GetCategoryResponse)
            }

            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                homeInterface.onGetCategoryFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetLocation(userId : Int, isSelected : Boolean){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getLocation(userId, isSelected).enqueue(object :
            Callback<GetLocationResponse> {
            override fun onResponse(
                call: Call<GetLocationResponse>,
                response: Response<GetLocationResponse>
            ) {
                homeInterface.onGetLocationSuccess(response.body() as GetLocationResponse)
            }

            override fun onFailure(call: Call<GetLocationResponse>, t: Throwable) {
                homeInterface.onGetLocationFailure(t.message ?: "통신 오류")
            }

        })
    }
}