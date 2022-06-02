package com.risingcamp.coupangeats.src.home.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityStoreBinding
import com.risingcamp.coupangeats.src.MainActivity
import com.risingcamp.coupangeats.src.home.store.models.getStoreAllMenu.GetStoreAllMenuResponse
import com.risingcamp.coupangeats.src.home.store.models.getStoreAllMenu.Result
import com.risingcamp.coupangeats.src.home.store.models.getStoreCategory.GetStoreCategoryResponse
import com.risingcamp.coupangeats.src.home.store.models.getStoreMain.GetStoreMainResponse
import org.jetbrains.anko.share
import java.util.*

class StoreActivity: BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate),StoreInterface {

    lateinit var adapter : MenuImgAdapter
    var currentPosition = 0
    private val intervalTime = 5000.toLong()

    var sharedPreference : Int? = null

    var restaurantId : Int? = null
    var restaurantId2 : Int? = null

    var delivery_start : String? = null
    var delivery_end : String? = null
    var pack_start : String? = null
    var pack_end : String? = null

    var delivery_fee : String? = null
    var order_price : String? = null

    var deliveryFragment = DeliveryFragment()
    var packFragment = PackFragment()

    var menuList1 = mutableListOf<Result>()
    var menuList2 = mutableListOf<Result>()
    var menuList3 = mutableListOf<Result>()
    var menuList4 = mutableListOf<Result>()
    var menuList5 = mutableListOf<Result>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreference = ApplicationClass.sSharedPreferences.getInt("FirstRestaurantId", 0)
        //restaurantId = intent.getIntExtra("restaurantId", 0)
        Log.d("레스토랑아이디", "$sharedPreference")

        StoreService(this).tryGetStoreMain(sharedPreference!!)

        //setSupportActionBar(binding.storeToolbar)	//툴바 사용 설정
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 뒤로가기 보이게
        setStatusBarTransparent()
        binding.storeToolbar.setPadding(0, statusBarHeight(), 0, 0)

        setBackBtn()

        setPage()

        setTabLayout()
        setMenuTabLayout()
        setScrollListener()

        setMenuList()
    }

    fun setBackBtn(){
        binding.storeToolbarBack.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().remove("FirstRestaurantId").apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.storeToolbarBack2.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().remove("FirstRestaurantId").apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //상단 탭
    fun setTabLayout(){
        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))
    }

    //메뉴 탭
    fun setMenuTabLayout(){
        StoreService(this).tryGetStoreCategory(sharedPreference!!)
    }

    //스크롤리스너
    @RequiresApi(Build.VERSION_CODES.M)
    fun setScrollListener(){
        binding.storeNestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            var tb = binding.storeMidTabLayout.top
            var toolbar = binding.storeToolbar2.top
            Log.d("스크롤", "$scrollY, $tb, $toolbar")

            if(scrollY + (binding.storeMidTabLayout.bottom - binding.storeCollapsing.top) > binding.storeNestedScroll.top){
                binding.storeToolbar2.visibility = View.VISIBLE
            } else{
                binding.storeToolbar2.visibility = View.GONE
            }

            if(scrollY==0 || scrollY < binding.storeLine1.top){
                binding.storeMidTabLayout.setScrollPosition(0, 0f, true)
            }
            if(scrollY > binding.storeLine1.top){
                binding.storeMidTabLayout.setScrollPosition(1, 0f, true)
            }
            if(scrollY > binding.storeLine2.top){
                binding.storeMidTabLayout.setScrollPosition(2, 0f, true)
            }
            if(scrollY > binding.storeLine3.top){
                binding.storeMidTabLayout.setScrollPosition(3, 0f, true)
            }
        }
    }

    //상단 이미지 관련
    fun setPage() {
        binding.storeTopVp.setCurrentItem((Int.MAX_VALUE / 2)-3, true)
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
                binding.storeTopVp.setCurrentItem(currentPosition+1,true)
                autoScrollStart(intervalTime)
            }
        }
    }

    //메뉴리스트
    fun setMenuList(){
        StoreService(this).tryGetStoreAllMenu(sharedPreference!!)
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
    override fun onGetStoreMainSuccess(getStoreMainResponse: GetStoreMainResponse) {

        restaurantId2 = getStoreMainResponse.result.restaurantId
        //intent.putExtra("restaurantId2", restaurantId2)
        ApplicationClass.sSharedPreferences.edit().putInt("RestaurantId", restaurantId2!!).apply()


        binding.storeBigName.text = getStoreMainResponse.result.resName
        binding.storeToolbarName2.text = getStoreMainResponse.result.resName

        var cheetah = getStoreMainResponse.result.isCheetah
        if(cheetah == 1){
            binding.storeCheetah.visibility = View.VISIBLE
        } else{
            binding.storeCheetah.visibility = View.GONE
        }

        binding.storeStarPoint.text = getStoreMainResponse.result.starPoint.toString()
        binding.storeReviewCount.text = getStoreMainResponse.result.reviewCount.toString()

        delivery_start = getStoreMainResponse.result.deliveryTime.toString()
        binding.storeDeliveryTabTimeStart.text = delivery_start
        delivery_end = (getStoreMainResponse.result.deliveryTime+5).toString()
        binding.storeDeliveryTabTimeEnd.text = delivery_end

        pack_start = (getStoreMainResponse.result.deliveryTime-10).toString()
        binding.storePackTabTimeStart.text = pack_start
        pack_end = getStoreMainResponse.result.deliveryTime.toString()
        binding.storePackTabTimeEnd.text = pack_end

        delivery_fee = getStoreMainResponse.result.minDeliveryFee.toString()
        order_price = getStoreMainResponse.result.minOrderPrice.toString()

        var img_1 = getStoreMainResponse.result.resImageUrlList[0]
        var img_2 = getStoreMainResponse.result.resImageUrlList[1]
        var img_3 = getStoreMainResponse.result.resImageUrlList[2]

        //배너 세팅
        var models = arrayListOf(img_1, img_2, img_3)
        var count = models.size
        adapter = MenuImgAdapter(models, this)
        binding.storeTopVp.adapter = adapter

        //페이지가 바꼈을때 호출
        binding.storeTopVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.storeMenuImtIndicatorNum.text = ((position%3)+1).toString()
                currentPosition = position
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        //탭 리스너
        binding.storeTopTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            //탭 선택할때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text){
                    "배달" -> {
                        transaction.replace(R.id.store_frm, deliveryFragment).commitAllowingStateLoss()
                        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))

                        Log.d("액티비티", "$delivery_start, $delivery_end")
                    }
                    "포장" -> {
                        transaction.replace(R.id.store_frm, packFragment).commitAllowingStateLoss()
                        binding.storePackTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabSlash.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storePackTabMin.setTextColor(Color.parseColor("#00AFFE"))
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

        var bundle = Bundle()
        bundle.putString("deliveryStart", delivery_start)
        bundle.putString("deliveryEnd", delivery_end)

        bundle.putString("deliveryFee", delivery_fee)
        bundle.putString("orderPrice", order_price)

        Log.d("번들", "$delivery_start, $delivery_end")

        bundle.putString("packStart", pack_start)
        bundle.putString("packEnd", pack_end)
        Log.d("번들2", "$pack_start, $pack_end")

        deliveryFragment.arguments = bundle
        packFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.store_frm, deliveryFragment).commitAllowingStateLoss()
    }
    override fun onGetStoreMainFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


    override fun onGetStoreCategorySuccess(getStoreCategoryResponse: GetStoreCategoryResponse) {
        var CategorySize = getStoreCategoryResponse.result.size
        Log.d("사이즈", "$CategorySize")

        for(i in 0 until CategorySize){
            binding.storeMidTabLayout.addTab(binding.storeMidTabLayout.newTab().setText(getStoreCategoryResponse.result[i].kindName))
        }

        if(getStoreCategoryResponse.result[0].kindName.isNotEmpty()){
            binding.storeListCategory1.text = getStoreCategoryResponse.result[0].kindName
        } else{
            binding.storeListCategory1.visibility = View.GONE
            binding.storeList1.visibility = View.GONE
        }

        if(getStoreCategoryResponse.result[1].kindName.isNotEmpty()){
            binding.storeListCategory2.text = getStoreCategoryResponse.result[1].kindName
        } else{
            binding.storeListCategory2.visibility = View.GONE
            binding.storeList2.visibility = View.GONE
        }

        if(getStoreCategoryResponse.result[2].kindName.isNotEmpty()){
            binding.storeListCategory3.text = getStoreCategoryResponse.result[2].kindName
        } else{
            binding.storeListCategory3.visibility = View.GONE
            binding.storeList3.visibility = View.GONE
        }

        if(getStoreCategoryResponse.result[3].kindName.isNotEmpty()){
            binding.storeListCategory4.text = getStoreCategoryResponse.result[3].kindName
        } else{
            binding.storeListCategory4.visibility = View.GONE
            binding.storeList4.visibility = View.GONE
        }

        if(getStoreCategoryResponse.result[4].kindName.isNotEmpty()){
            binding.storeListCategory5.text = getStoreCategoryResponse.result[4].kindName
        } else{
            binding.storeListCategory5.visibility = View.GONE
            binding.storeList5.visibility = View.GONE
        }


        binding.storeMidTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            //탭 선택할때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> { binding.storeNestedScroll.scrollTo(0, 0) }
                    1 -> { binding.storeNestedScroll.scrollTo(0, binding.storeLine1.bottom) }
                    2 -> { binding.storeNestedScroll.scrollTo(0, binding.storeLine2.bottom) }
                    3 -> { binding.storeNestedScroll.scrollTo(0, binding.storeLine3.bottom) }
                    4 -> { binding.storeNestedScroll.scrollTo(0, binding.storeLine4.bottom) }
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
    override fun onGetStoreCategoryFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


    override fun onGetStoreAllMenuSuccess(getStoreAllMenuResponse: GetStoreAllMenuResponse) {
        var menuListSize = getStoreAllMenuResponse.result.size

        for(i in 0 until menuListSize){
            if(getStoreAllMenuResponse.result[i].kindId == 1){
                menuList1.add(getStoreAllMenuResponse.result[i])

            } else if(getStoreAllMenuResponse.result[i].kindId == 2){
                menuList2.add(getStoreAllMenuResponse.result[i])

            } else if(getStoreAllMenuResponse.result[i].kindId == 3){
                menuList3.add(getStoreAllMenuResponse.result[i])

            } else if(getStoreAllMenuResponse.result[i].kindId == 4){
                menuList4.add(getStoreAllMenuResponse.result[i])

            } else if(getStoreAllMenuResponse.result[i].kindId == 5){
                menuList5.add(getStoreAllMenuResponse.result[i])
            }
        }

        //리스트1
        Log.d("메뉴리스트1", "$menuList1")
        binding.storeList1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList1.setHasFixedSize(true)
        binding.storeList1.adapter = StoreListAdapter(menuList1)

        //리스트2
        Log.d("메뉴리스트2", "$menuList2")
        binding.storeList2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList2.setHasFixedSize(true)
        binding.storeList2.adapter = StoreListAdapter(menuList2)

        //리스트3
        Log.d("메뉴리스트3", "$menuList3")
        binding.storeList3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList3.setHasFixedSize(true)
        binding.storeList3.adapter = StoreListAdapter(menuList3)

        //리스트4
        Log.d("메뉴리스트4", "$menuList4")
        binding.storeList4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList4.setHasFixedSize(true)
        binding.storeList4.adapter = StoreListAdapter(menuList4)

        //리스트5
        Log.d("메뉴리스트5", "$menuList5")
        binding.storeList5.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList5.setHasFixedSize(true)
        binding.storeList5.adapter = StoreListAdapter(menuList5)

    }
    override fun onGetStoreAllMenuFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


}