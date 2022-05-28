package com.risingcamp.coupangeats.src.home.store

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        supportActionBar!!.setDisplayShowTitleEnabled(false) // 타이틀 보이게
        //binding.storeToolbar.setTitle("가게이름")

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

}