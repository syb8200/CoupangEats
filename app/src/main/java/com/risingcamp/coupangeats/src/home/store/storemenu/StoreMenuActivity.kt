package com.risingcamp.coupangeats.src.home.store.storemenu

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityStoreMenuBinding
import com.risingcamp.coupangeats.src.home.ResListAdapter
import com.risingcamp.coupangeats.src.home.store.MenuImgAdapter
import com.risingcamp.coupangeats.src.home.store.StoreActivity
import com.risingcamp.coupangeats.src.home.store.storemenu.models.GetStoreMenuOptionResponse
import java.text.DecimalFormat

class StoreMenuActivity: BaseActivity<ActivityStoreMenuBinding>(ActivityStoreMenuBinding::inflate),StoreMenuInterface {

    var restaurantId : Int? = null
    var menuId : Int? = null

    lateinit var adapter : StoreMenuImgAdapter
    var currentPosition = 0
    private val intervalTime = 5000.toLong()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarTransparent()
        binding.storeMenuToolbar.setPadding(0, statusBarHeight(), 0, 0)

        setStoreMenu()
        setBackBtn()

        setScrollListener()
    }

    fun setStoreMenu(){
        val sharedPreference = ApplicationClass.sSharedPreferences.getInt("RestaurantId", 0)
        menuId = intent.getIntExtra("menuId", 0)

        StoreMenuService(this).tryGetStoreMenuOption(sharedPreference, menuId!!)
    }

    fun setBackBtn(){
        binding.storeMenuToolbarBack.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().remove("RestaurantId").apply()
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }

        binding.storeMenuToolbarBack2.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().remove("RestaurantId").apply()
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }
    }

    //상단 이미지 관련
    fun setPage() {
        binding.storeMenuTopVp.setCurrentItem((Int.MAX_VALUE / 2), true)
    }

    private fun autoScrollStart(intervalTime : Long){
        menuItemImgHandler().removeMessages(0) // 이거 안하면 핸들러가 여러개로 계속 늘어남
        menuItemImgHandler().sendEmptyMessageDelayed(0, intervalTime) // intervalTime만큼 반복해서 핸들러 실행
    }
    private fun autoScrollStop(){
        menuItemImgHandler().removeMessages(0)
    }

    private inner class menuItemImgHandler: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 0){
                binding.storeMenuTopVp.setCurrentItem(currentPosition+1,true)
                autoScrollStart(intervalTime)
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun setScrollListener(){
        binding.storeMenuNestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if(scrollY > binding.storeMenuNestedScroll.top){
                binding.storeMenuToolbar2.visibility = View.VISIBLE
            } else {
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

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatusBarOrigin()
    }

//--------------------------------------------------------------------------------------------------------------------------------
    override fun onGetStoreMenuOptionSuccess(getGetStoreMenuOptionResponse: GetStoreMenuOptionResponse) {
        var check_menu = getGetStoreMenuOptionResponse.result
        Log.d("메뉴확인", "$check_menu")

        //메뉴 정보 세팅
        if(getGetStoreMenuOptionResponse.result.resMenuInfo.menuImageUrlList.size == 1){
            var img_0 = getGetStoreMenuOptionResponse.result.resMenuInfo.menuImageUrlList[0]

            binding.storeMenuTopImv.visibility = View.VISIBLE
            binding.storeMenuTopVp.visibility = View.INVISIBLE
            binding.storeMenuImgIndicator.visibility = View.INVISIBLE
            binding.storeMenuImgIndicatorLayout.visibility = View.INVISIBLE

            Glide.with(this)
                .load(img_0)
                .into(binding.storeMenuTopImv)

        } else{
            var img_1 = getGetStoreMenuOptionResponse.result.resMenuInfo.menuImageUrlList[0]
            var img_2 = getGetStoreMenuOptionResponse.result.resMenuInfo.menuImageUrlList[1]

            binding.storeMenuTopImv.visibility = View.INVISIBLE
            binding.storeMenuTopVp.visibility = View.VISIBLE
            binding.storeMenuImgIndicator.visibility = View.VISIBLE
            binding.storeMenuImgIndicatorLayout.visibility = View.VISIBLE

            //상단 이미지 vp
            var models = arrayListOf(img_1, img_2)
            var count = models.size
            adapter = StoreMenuImgAdapter(models, this)
            binding.storeMenuTopVp.adapter = adapter

            //페이지가 바꼈을때 호출
            binding.storeMenuTopVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.storeMenuImgIndicatorNum.text = ((position%2)+1).toString()
                    currentPosition = position
                }
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }

        binding.storeMenuName.text = getGetStoreMenuOptionResponse.result.resMenuInfo.menuName
        binding.storeMenuToolbarName2.text = getGetStoreMenuOptionResponse.result.resMenuInfo.menuName

        var menuPrice = getGetStoreMenuOptionResponse.result.resMenuInfo.menuPrice
        var frmt = DecimalFormat("#,###")
        binding.storeMenuPrice.text = frmt.format(menuPrice).toString()

        if(getGetStoreMenuOptionResponse.result.resMenuInfo.menuDescription == null){
            binding.storeMenuDes.visibility = View.GONE
        } else{
            binding.storeMenuDes.text = getGetStoreMenuOptionResponse.result.resMenuInfo.menuDescription
        }


        //메뉴 옵션 세팅
        binding.storeMenuOptionTitle.text = getGetStoreMenuOptionResponse.result.resMenuOptionList[0].optionKindName
        binding.storeMenuOptionTitle2.text = getGetStoreMenuOptionResponse.result.resMenuOptionList[1].optionKindName

        var count = 1
        binding.storeMenuPlusBtn.setOnClickListener {
            count += 1
            binding.storeMenuCount.text = count.toString()

            var price = getGetStoreMenuOptionResponse.result.resMenuInfo.menuPrice * count
            var frmt2 = DecimalFormat("#,###")
            binding.storeMenuPrice.text = frmt2.format(price).toString()

        }
    

        if(getGetStoreMenuOptionResponse.result.resMenuOptionList[0].isEssential == true){
            binding.storeMenuOptionEssential.visibility = View.VISIBLE
        } else{
            binding.storeMenuOptionEssential.visibility = View.INVISIBLE
        }

        if(getGetStoreMenuOptionResponse.result.resMenuOptionList[1].isEssential == true){
            binding.storeMenuOptionEssential2.visibility = View.VISIBLE
        } else{
            binding.storeMenuOptionEssential2.visibility = View.INVISIBLE
        }

        var menuOptionList1 = getGetStoreMenuOptionResponse.result.resMenuOptionList[0].optionInfoList
        binding.storeMenuList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeMenuList.setHasFixedSize(true)
        binding.storeMenuList.adapter = StoreMenuOption1Adapter(menuOptionList1)

        var menuOptionList2 = getGetStoreMenuOptionResponse.result.resMenuOptionList[1].optionInfoList
        binding.storeMenuList2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeMenuList2.setHasFixedSize(true)
        binding.storeMenuList2.adapter = StoreMenuOption2Adapter(menuOptionList2)



}
    override fun onGetStoreMenuOptionFailure(message: String) {
        Log.d("오류", "오류: $message")
    }

}