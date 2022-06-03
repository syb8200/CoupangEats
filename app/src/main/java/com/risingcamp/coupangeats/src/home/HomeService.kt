package com.risingcamp.coupangeats.src.home

import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.home.models.getMidBanner.GetMidBannerResponse
import com.risingcamp.coupangeats.src.home.models.getNewRes.GetNewResResponse
import com.risingcamp.coupangeats.src.home.models.getResList.GetResListResponse
import com.risingcamp.coupangeats.src.home.models.getTopBanner.GetTopBannerResponse
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

    fun tryGetFranRes(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getFranRes().enqueue(object :
            Callback<GetFranResResponse> {
            override fun onResponse(
                call: Call<GetFranResResponse>,
                response: Response<GetFranResResponse>
            ) {
                homeInterface.onGetFranResSuccess(response.body() as GetFranResResponse)
            }

            override fun onFailure(call: Call<GetFranResResponse>, t: Throwable) {
                homeInterface.onGetFranResFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetNewRes(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getNewRes().enqueue(object :
            Callback<GetNewResResponse> {
            override fun onResponse(
                call: Call<GetNewResResponse>,
                response: Response<GetNewResResponse>
            ) {
                homeInterface.onGetNewResSuccess(response.body() as GetNewResResponse)
            }

            override fun onFailure(call: Call<GetNewResResponse>, t: Throwable) {
                homeInterface.onGetNewResFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetTopBanner(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getTopBanner().enqueue(object :
            Callback<GetTopBannerResponse> {
            override fun onResponse(
                call: Call<GetTopBannerResponse>,
                response: Response<GetTopBannerResponse>
            ) {
                homeInterface.onGetTopBannerSuccess(response.body() as GetTopBannerResponse)
            }

            override fun onFailure(call: Call<GetTopBannerResponse>, t: Throwable) {
                homeInterface.onGetTopBannerFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetMidBanner(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getMidBanner().enqueue(object :
            Callback<GetMidBannerResponse> {
            override fun onResponse(
                call: Call<GetMidBannerResponse>,
                response: Response<GetMidBannerResponse>
            ) {
                homeInterface.onGetMidBannerSuccess(response.body() as GetMidBannerResponse)
            }

            override fun onFailure(call: Call<GetMidBannerResponse>, t: Throwable) {
                homeInterface.onGetMidBannerFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetResList(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getResList().enqueue(object :
            Callback<GetResListResponse> {
            override fun onResponse(
                call: Call<GetResListResponse>,
                response: Response<GetResListResponse>
            ) {
                homeInterface.onGetResListSuccess(response.body() as GetResListResponse)
            }

            override fun onFailure(call: Call<GetResListResponse>, t: Throwable) {
                homeInterface.onGetResListFailure(t.message ?: "통신 오류")
            }

        })
    }
}