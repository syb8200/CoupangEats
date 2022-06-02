package com.risingcamp.coupangeats.src.home.store.storemenu

import com.risingcamp.coupangeats.src.home.store.storemenu.models.GetStoreMenuOptionResponse

interface StoreMenuInterface {

    fun onGetStoreMenuOptionSuccess(getGetStoreMenuOptionResponse: GetStoreMenuOptionResponse)

    fun onGetStoreMenuOptionFailure(message: String)
}