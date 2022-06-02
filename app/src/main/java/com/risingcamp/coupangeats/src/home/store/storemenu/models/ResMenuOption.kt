package com.risingcamp.coupangeats.src.home.store.storemenu.models

data class ResMenuOption(
    val isEssential: Boolean,
    val optionInfoList: List<OptionInfo>,
    val optionKindId: Int,
    val optionKindMaxCount: Int,
    val optionKindName: String
)