package com.risingcamp.coupangeats.src.home.models.getTopBanner

data class GetTopBannerResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<TopBannerResult>
)