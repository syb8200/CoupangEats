package com.risingcamp.coupangeats.src.home.store.storemenu

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.marginBottom
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityStoreMenuBinding

class StoreMenuActivity: BaseActivity<ActivityStoreMenuBinding>(ActivityStoreMenuBinding::inflate) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarTransparent()
        binding.storeMenuToolbar.setPadding(0, statusBarHeight(), 0, 0)

        setScrollListener()

    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun setScrollListener(){
        binding.storeMenuNestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if(scrollY+statusBarHeight() > binding.storeMenuNestedScroll.top){
                binding.storeMenuToolbar2.visibility = View.VISIBLE
            } else{
                binding.storeMenuToolbar2.visibility = View.GONE
            }
        }
    }

    // 상태바 (Status Bar) 투명색 + 상태바와 UI 겹쳐보이는 것 방지 하기 위해 Padding 적용
    fun Activity.setStatusBarTransparent(){
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30){
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    fun Context.statusBarHeight(): Int{
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }

    fun Activity.setStatusBarOrigin(){
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        if(Build.VERSION.SDK_INT >= 30){
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatusBarOrigin()
    }

}