package com.risingcamp.coupangeats.src

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.ListFragment
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityMainBinding
import com.risingcamp.coupangeats.src.favorite.FavoriteActivity
import com.risingcamp.coupangeats.src.home.HomeFragment
import com.risingcamp.coupangeats.src.myeats.MyEatsFragment
import com.risingcamp.coupangeats.src.orderlist.OrderListFragment
import com.risingcamp.coupangeats.src.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.menu_main_btm_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_favorite -> {
                    //액티비티
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_orderlist -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, OrderListFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_myeats -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyEatsFragment())
                        .addToBackStack(null) /*핸드폰 자체 뒤로가기*/
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }
}