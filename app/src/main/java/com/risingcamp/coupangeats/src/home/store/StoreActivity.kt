package com.risingcamp.coupangeats.src.home.store

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseActivity
import com.risingcamp.coupangeats.databinding.ActivityStoreBinding

class StoreActivity: BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate) {

    lateinit var adapter : MenuImgAdapter
    var currentPosition = 0
    private val intervalTime = 5000.toLong()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setSupportActionBar(binding.storeToolbar)	//툴바 사용 설정
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 뒤로가기 보이게

        setStatusBarTransparent()
        binding.storeToolbar.setPadding(0, statusBarHeight(), 0, 0)

        setMenuImg()
        setPage()

        setTabLayout()
        setMenuTabLayout()
        setScrollListener()

        setStoreList1()
        setStoreList2()
        setStoreList3()
        setStoreList4()

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

    //상단 탭
    fun setTabLayout(){
        supportFragmentManager.beginTransaction().replace(R.id.store_frm, DeliveryFragment()).commitAllowingStateLoss()

        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))

        binding.storeTopTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            //탭 선택할때 이벤트
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text){
                    "배달" -> {
                        transaction.replace(R.id.store_frm, DeliveryFragment()).commitAllowingStateLoss()
                        binding.storeDeliveryTabTimeStart.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabSlash.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabTimeEnd.setTextColor(Color.parseColor("#00AFFE"))
                        binding.storeDeliveryTabMin.setTextColor(Color.parseColor("#00AFFE"))
                    }
                    "포장" -> {
                        transaction.replace(R.id.store_frm, PackFragment()).commitAllowingStateLoss()
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
    }

    fun setMenuTabLayout(){
        binding.storeMidTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text){
                    "추천메뉴" -> {
                        binding.storeNestedScroll.scrollTo(0, 0)
                    }
                    "세트메뉴" -> {
                        binding.storeNestedScroll.scrollTo(0, binding.storeLine1.bottom)
                    }
                    "피자메뉴" -> {
                        binding.storeNestedScroll.scrollTo(0, binding.storeLine2.bottom)
                    }
                    "비밀메뉴" -> {
                        binding.storeNestedScroll.scrollTo(0, binding.storeLine3.bottom)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }



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

    fun setMenuImg(){
        var models = arrayListOf(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, R.drawable.ic_cp_logo)
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

    }

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

    fun setStoreList1(){
        val storeList1 = arrayListOf(
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo)
        )
        binding.storeList1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList1.setHasFixedSize(true)
        binding.storeList1.adapter = StoreListAdapter(storeList1)
    }

    fun setStoreList2(){
        val storeList2 = arrayListOf(
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo)
        )
        binding.storeList2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList2.setHasFixedSize(true)
        binding.storeList2.adapter = StoreListAdapter(storeList2)
    }

    fun setStoreList3(){
        val storeList3 = arrayListOf(
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo)
        )
        binding.storeList3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList3.setHasFixedSize(true)
        binding.storeList3.adapter = StoreListAdapter(storeList3)
    }

    fun setStoreList4(){
        val storeList4 = arrayListOf(
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo),
            StoreList("깐풍기요리치킨+펩시1.25",20000,"5/1~6/30 멕시카나 메뉴 6종 주문시 펩시 1.25 무료 사이즈업", R.drawable.ic_cp_logo)
        )
        binding.storeList4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.storeList4.setHasFixedSize(true)
        binding.storeList4.adapter = StoreListAdapter(storeList4)
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


}