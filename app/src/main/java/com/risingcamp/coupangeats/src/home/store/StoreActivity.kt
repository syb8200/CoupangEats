package com.risingcamp.coupangeats.src.home.store

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.google.android.material.tabs.TabLayout
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityStoreBinding
import com.risingcamp.coupangeats.src.home.HomeFragment

class StoreActivity: BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.storeToolbar)	//툴바 사용 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 뒤로가기 보이게
        //supportActionBar!!.setDisplayShowTitleEnabled(false) // 타이틀 보이게
        //binding.storeToolbar.setTitle("가게이름")

        setStatusBarTransparent()
        binding.storeToolbar.setPadding(0, statusBarHeight(), 0, navigationHeight())

        setTabLayout()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_store_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.menu_store_toolbar_share -> {

            }
            R.id.menu_store_toolbar_share -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setTabLayout(){
        supportFragmentManager.beginTransaction().replace(R.id.store_frm, DeliveryFragment()).commitAllowingStateLoss()

        binding.storeTopTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            //탭 선택할때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text){
                    "배달" -> transaction.replace(R.id.store_frm, DeliveryFragment()).commitAllowingStateLoss()
                    "포장" -> transaction.replace(R.id.store_frm, PackFragment()).commitAllowingStateLoss()
                }
            }
            //다른 탭 버튼 눌러 선택된 탭 버튼 해제될 때 이벤트
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            //선택된 탭 버튼을 다시 선택할 때 이벤트
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
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

    fun Context.navigationHeight(): Int{
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
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