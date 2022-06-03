package com.risingcamp.coupangeats.src.home.store.storemenu.cart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityCartBinding

class CartActivity: BaseActivity<ActivityCartBinding>(ActivityCartBinding::inflate) {

    var time : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBackBtn()

        setTabLayout()
        setBottomDialog()

        binding.cartRequestToGetForkCheckbox.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.cartRequestToOwnerEdt.windowToken, 0)
            binding.cartRequestToOwnerEdt.clearFocus()
        }

    }

    fun setBackBtn(){
        binding.cartBack.setOnClickListener {

        }
    }


    //상단 탭
    fun setTabLayout(){
        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))

        time = ApplicationClass.sSharedPreferences.getString("DeliveryTime", null)
        Log.d("배달시간", "$time")

        var delivery_start = time!!.toInt()
        var delivery_end = delivery_start + 5
        var pack_start = delivery_start - 10

        binding.storeDeliveryTabTimeStart.text = delivery_start.toString()
        binding.storeDeliveryTabTimeEnd.text = delivery_end.toString()
        binding.storePackTabTimeStart.text = pack_start.toString()
        binding.storePackTabTimeEnd.text = delivery_start.toString()

        supportFragmentManager.beginTransaction().replace(R.id.cart_frm, CartDeliveryFragment()).commitAllowingStateLoss()

        //탭 리스너
        binding.cartTopTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            //탭 선택할때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text){
                    "배달" -> {
                        transaction.replace(R.id.cart_frm, CartDeliveryFragment()).commitAllowingStateLoss()
                        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))

                        Log.d("액티비티", "$delivery_start, $delivery_end")

                        binding.cartRequestToDeliveryTxt.visibility = View.VISIBLE
                        binding.cartRequestToDeliveryTv.visibility = View.VISIBLE
                    }
                    "포장" -> {
                        transaction.replace(R.id.cart_frm, CartPackFragment()).commitAllowingStateLoss()
                        binding.storePackTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabSlash.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabMin.setTextColor(Color.parseColor("#00AFFE"))

                        binding.cartRequestToDeliveryTxt.visibility = View.GONE
                        binding.cartRequestToDeliveryTv.visibility = View.GONE
                    }
                }
            }
            //다른 탭 버튼 눌러 선택된 탭 버튼 해제될 때 이벤트
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#000000"))
                binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#000000"))
                binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#000000"))
                binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#000000"))

                binding.storePackTabTimeStart.setTextColor(Color.parseColor("#000000"))
                binding.storePackTabSlash.setTextColor(Color.parseColor("#000000"))
                binding.storePackTabTimeEnd.setTextColor(Color.parseColor("#000000"))
                binding.storePackTabMin.setTextColor(Color.parseColor("#000000"))
            }
            //선택된 탭 버튼을 다시 선택할 때 이벤트
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    fun setBottomDialog(){
        binding.cartRequestToDeliveryTv.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.setContentView(R.layout.dialog_cart_delivery_option)

            bottomSheet.setCanceledOnTouchOutside(true)

            bottomSheet.create()
            bottomSheet.show()
        }
    }



}