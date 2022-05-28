package com.risingcamp.coupangeats.src.home.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.databinding.FragmentStorePackBinding

class PackFragment : BaseFragment<FragmentStorePackBinding>(FragmentStorePackBinding::bind, R.layout.fragment_store_pack){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}