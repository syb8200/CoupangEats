package com.risingcamp.coupangeats.src.home.models.getMidBanner

data class GetMidBannerResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<MidBannerResult>
)