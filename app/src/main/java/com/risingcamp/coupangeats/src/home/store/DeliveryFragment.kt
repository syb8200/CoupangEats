package com.risingcamp.coupangeats.src.home.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentStoreDeliveryBinding

class DeliveryFragment: BaseFragment<FragmentStoreDeliveryBinding>(FragmentStoreDeliveryBinding::bind, R.layout.fragment_store_delivery) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var deliveryStart = arguments?.getString("deliveryStart", null)
        var deliveryEnd = arguments?.getString("deliveryEnd", null)

        var deliveryFee = arguments?.getString("deliveryFee", null)
        var orderPrice = arguments?.getString("orderPrice", null)

        Log.d("배달 확인", "$deliveryStart, $deliveryEnd, $deliveryFee, $orderPrice")

        binding.frgStoreDeliveryTimeStart.text = deliveryStart.toString()
        binding.frgStoreDeliveryTimeEnd.text = deliveryEnd.toString()
        binding.frgStoreDeliveryFeePrice.text = deliveryFee.toString()
        binding.frgStoreOrderMinPrice.text = orderPrice.toString()

    }


}