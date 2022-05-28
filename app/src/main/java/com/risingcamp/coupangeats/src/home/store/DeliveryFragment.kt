package com.risingcamp.coupangeats.src.home.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.databinding.FragmentStoreDeliveryBinding

class DeliveryFragment: BaseFragment<FragmentStoreDeliveryBinding>(FragmentStoreDeliveryBinding::bind, R.layout.fragment_store_delivery) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}