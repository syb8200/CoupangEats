package com.risingcamp.coupangeats.src.home.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.databinding.FragmentStorePackBinding

class PackFragment : BaseFragment<FragmentStorePackBinding>(FragmentStorePackBinding::bind, R.layout.fragment_store_pack){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var packStart = arguments?.getString("packStart", null)
        var packEnd = arguments?.getString("packEnd", null)

        Log.d("배달 확인1", "$packStart, $packEnd")

        binding.frgStorePackTimeStart.text = packStart.toString()
        binding.frgStorePackTimeEnd.text = packEnd.toString()
    }
}