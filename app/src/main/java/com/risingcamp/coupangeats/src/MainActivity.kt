package com.risingcamp.coupangeats.src

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.risingcamp.coupangeats.config.ApplicationClass.Companion.sSharedPreferences
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityMainBinding
import com.risingcamp.coupangeats.src.favorite.FavoriteActivity
import com.risingcamp.coupangeats.src.home.HomeFragment
import com.risingcamp.coupangeats.src.login.LoginActivity
import com.risingcamp.coupangeats.src.models.GetAutoLoginResponse
import com.risingcamp.coupangeats.src.myeats.MyEatsFragment
import com.risingcamp.coupangeats.src.orderlist.OrderListFragment
import com.risingcamp.coupangeats.src.search.SearchFragment
import com.risingcamp.coupangeats.src.signup.SignupActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),AutoLoginInterface {

    var check : String? = null
    var jwt : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        check = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        //var check = ApplicationClass.sSharedPreferences.getString("X-ACCESS-TOKEN",null).toString()
        Log.d("SP", "$check")

        AutoLoginService(this).tryPostAutoLogin()

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.menu_main_btm_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .addToBackStack(null) /*핸드폰 자체 뒤로가기*/
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
                    if(check != jwt || check==null){
                        checkLogin()
                    } else{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, OrderListFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.menu_main_btm_nav_myeats -> {

                    if(check != jwt || check==null){
                        checkLogin()
                    } else{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyEatsFragment())
                            .addToBackStack(null) /*핸드폰 자체 뒤로가기*/
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }
            }
            false
        }
    }

    fun checkLogin(){
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(R.layout.dialog_login_bottomsheet)

        bottomSheet.setCanceledOnTouchOutside(true)

        bottomSheet.create()
        bottomSheet.show()

        var email_login = bottomSheet.findViewById<TextView>(R.id.login_btm_sheet_email_login)
        var signup = bottomSheet.findViewById<TextView>(R.id.login_btm_sheet_signup)

        email_login!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        signup!!.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onGetAutoLoginSuccess(getAutoLoginResponse: GetAutoLoginResponse) {
        jwt = getAutoLoginResponse.result.jwt
        Log.d("오토로그인", "$jwt")

        if(jwt == null){
            Log.d("오토로그인 실패", "$jwt")
        }
    }

    override fun onGetAutoLoginFailure(message: String) {
        Log.d("오류", "오류: $message")
    }

}