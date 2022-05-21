package com.risingcamp.coupangeats.src.myeats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentMyeatsBinding

class MyEatsFragment: BaseFragment<FragmentMyeatsBinding>(FragmentMyeatsBinding::bind, R.layout.fragment_myeats) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}