package com.risingcamp.coupangeats.src.home.store

import com.risingcamp.coupangeats.src.home.store.models.getStoreCategory.GetStoreCategoryResponse
import com.risingcamp.coupangeats.src.home.store.models.getStoreMain.GetStoreMainResponse

interface StoreInterface {

    fun onGetStoreMainSuccess(getStoreMainResponse: GetStoreMainResponse)

    fun onGetStoreMainFailure(message: String)

    fun onGetStoreCategorySuccess(getStoreCategoryResponse: GetStoreCategoryResponse)

    fun onGetStoreCategoryFailure(message: String)
}