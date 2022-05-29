package com.risingcamp.coupangeats.src.home

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.src.MainActivity
import com.risingcamp.coupangeats.src.home.location.LocationActivity
import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.Result
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.login.LoginActivity
import com.risingcamp.coupangeats.src.signup.SignupActivity

data class categoryList(val categoryImg:Int, val categoryTxt:String)

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),HomeInterface{

    lateinit var adapter : TopBannerAdapter
    lateinit var adapter2 : MidBannerAdapter

    private var homeTopBannerHandler = HomeTopBannerHandler()
    private var homeMidBannerHandler = HomeMidBannerHandler()

    private val intervalTime = 5000.toLong()

    var currentPosition = 0
    var currentPosition2 = 0

    var listSize : Int? = null
    var incategoryList = MutableList(26, {""})
    //var incategoryList = mutableListOf<String>()

    var frag = MainActivity()

    var check : String? = null

    var hz_name_1 : String? = null
    var hz_rate_1 : Double? = null
    var hz_review_1 : Int? = null
    var hz_distance_1 : Double? = null
    var hz_delivery_1 : Int? = null
    var hz_img_1 : String? = null
    var hz_time_1 : Int? = null
    var hz_id_1 : Int? = null

    var option_recommend : Boolean? = null
    var option_cheetah : Boolean? = null
    var option_delivery : Boolean? = null
    var option_min_price : Boolean? = null
    var option_pack : Boolean? = null
    var option_coupon : Boolean? = null
    var option_drink : Boolean? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLocation()

        setTopBanner()
        setPage()

        setTopCategory()
        setCategory()

        setHorizontalResList()

        setMidBanner()

        setResListOption()
        setResList()

        setFabBtn()

        binding.homeResOptionScroll.translationZ = 1.0f
    }

    fun setLocation(){

        var userId = ApplicationClass.sSharedPreferences.getInt("UserId", 0)
        HomeService(this).tryGetLocation(userId, true)

        binding.homeTopLocationLayout.setOnClickListener {
            if(check == null){
                val bottomSheet = BottomSheetDialog(requireContext())
                bottomSheet.setContentView(R.layout.dialog_login_bottomsheet)

                bottomSheet.setCanceledOnTouchOutside(true)

                bottomSheet.create()
                bottomSheet.show()

                var email_login = bottomSheet.findViewById<TextView>(R.id.login_btm_sheet_email_login)
                var signup = bottomSheet.findViewById<TextView>(R.id.login_btm_sheet_signup)

                email_login!!.setOnClickListener {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }
                signup!!.setOnClickListener {
                    val intent = Intent(context, SignupActivity::class.java)
                    startActivity(intent)
                }
            } else{
                val intent = Intent(context, LocationActivity::class.java)
                startActivity(intent)

            }
        }
    }

    fun setTopBanner(){
        var models = arrayListOf(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, R.drawable.ic_cp_logo, R.drawable.ic_cp_logo, R.drawable.ic_cp_logo)
        var count = models.size
        adapter = TopBannerAdapter(models, requireContext())
        binding.homeTopBannerVp.adapter = adapter

        //페이지가 바꼈을때 호출
        binding.homeTopBannerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.homeTopBannerIndicatorNum.text = ((position%5)+1).toString()
                currentPosition = position
                binding.homeTopBannerIndicatorTotal.text = count.toString()
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    fun setMidBanner(){
        var models = arrayListOf(R.drawable.ic_cp_logo, R.drawable.ic_signup_email_complete)
        var count = models.size
        adapter2 = MidBannerAdapter(models, requireContext())
        binding.homeMidBannerVp.adapter = adapter2

        //페이지가 바꼈을때 호출
        binding.homeMidBannerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.homeMidBannerIndicatorNum.text = ((position%2)+1).toString()
                currentPosition2 = position
                binding.homeMidBannerIndicatorTotal.text = count.toString()
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

    }

    //배너 관련
    fun setPage() {
        binding.homeTopBannerVp.setCurrentItem((Int.MAX_VALUE / 2)-3, true)
        binding.homeMidBannerVp.setCurrentItem((Int.MAX_VALUE/2), true)
    }

    private fun autoScrollStart(intervalTime : Long){
        homeTopBannerHandler.removeMessages(0) // 이거 안하면 핸들러가 여러개로 계속 늘어남
        homeTopBannerHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime만큼 반복해서 핸들러 실행
    }
    private fun autoScrollStart2(intervalTime: Long){
        homeMidBannerHandler.removeMessages(0) // 이거 안하면 핸들러가 여러개로 계속 늘어남
        homeMidBannerHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime만큼 반복해서 핸들러 실행
    }

    private fun autoScrollStop(){
        homeTopBannerHandler.removeMessages(0)
    }
    private fun autoScrollStop2(){
        homeMidBannerHandler.removeMessages(0)
    }

    private inner class HomeTopBannerHandler: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 0){
                binding.homeTopBannerVp.setCurrentItem(currentPosition+1,true)
                autoScrollStart(intervalTime)
            }
        }
    }

    private inner class HomeMidBannerHandler : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 0){
                binding.homeMidBannerVp.setCurrentItem(currentPosition2+1, true)
                autoScrollStart2(intervalTime)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTopCategory(){
        HomeService(this).tryGetCategory()
        Log.d("확인", "$incategoryList")

        binding.homeTopCategory.visibility = View.INVISIBLE
        setScrollListener()
    }

    fun setCategory(){
        HomeService(this).tryGetCategory()
        Log.d("확인", "$incategoryList")
    }


    fun setHorizontalResList(){
        HomeService(this).tryGetFranRes()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setResListOption(){

        setScrollListener()

        //상단 옵션
        binding.homeResOptionAllMenu.setOnClickListener {
        }

        binding.homeResOptionRefresh.setOnClickListener {
        }

        binding.homeResOptionRecommend.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_home_option_recommend)
            bottomSheet.setCanceledOnTouchOutside(true)
            bottomSheet.create()
            bottomSheet.show()

            var recommend_close = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_close)
            var recommend_1 = bottomSheet.findViewById<TextView>(R.id.item_home_option_recommend_1)
            var recommend_1_check = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_1_check)
            var recommend_2 = bottomSheet.findViewById<TextView>(R.id.item_home_option_recommend_2)
            var recommend_2_check = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_2_check)
            var recommend_3 = bottomSheet.findViewById<TextView>(R.id.item_home_option_recommend_3)
            var recommend_3_check = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_3_check)
            var recommend_4 = bottomSheet.findViewById<TextView>(R.id.item_home_option_recommend_4)
            var recommend_4_check = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_4_check)
            var recommend_5 = bottomSheet.findViewById<TextView>(R.id.item_home_option_recommend_5)
            var recommend_5_check = bottomSheet.findViewById<ImageView>(R.id.item_home_option_recommend_5_check)
        }

        binding.homeResOptionCheetah.setOnClickListener {
        }

        binding.homeResOptionDeliveryFee.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_home_option_delivery)
            bottomSheet.setCanceledOnTouchOutside(true)
            bottomSheet.create()
            bottomSheet.show()
        }

        binding.homeResOptionMinPrice.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_home_option_min_price)
            bottomSheet.setCanceledOnTouchOutside(true)
            bottomSheet.create()
            bottomSheet.show()
        }

        binding.homeResOptionPack.setOnClickListener {
        }

        binding.homeResOptionCoupon.setOnClickListener {
        }

        binding.homeResOptionDrink.setOnClickListener {
        }
    }

    fun setResList(){

        val resListArrayList = arrayListOf(
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능"),
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능"),
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능"),
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능"),
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능"),
            ResList(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, "가나다", R.drawable.ic_cp_logo, "15", "25",
                4.1, 300, 1.9, 3000, "포장가능")
        )
        binding.homeResList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.homeResList.setHasFixedSize(true)
        binding.homeResList.adapter = ResListAdapter(resListArrayList)

    }



    /*
    fun setEndlessList(){

        binding.homeResList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //화면에 보이는 마지막 아이템의 position
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                //어댑터에 등록된 아이템의 총 개수 -1 (하단에서 마지막까지 다 뿌려졌는지 확인)
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                Log.d("position", "$lastVisibleItemPosition")

                //스크롤이 끝에 도달했는지 확인(최하단:1, 최상단:-1)
                if(!binding.homeResList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount){

                }

            }
        })
    }
     */



    @RequiresApi(Build.VERSION_CODES.M)
    fun setFabBtn(){
        binding.homeFloatingBtn.visibility = View.INVISIBLE
        setScrollListener()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setScrollListener(){
        binding.homeScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("스크롤", "$scrollY, $oldScrollY")

            //topCategory
            if(scrollY > binding.homeCategoryScroll.bottom){
                binding.homeTopCategory.visibility = View.VISIBLE
            } else{
                binding.homeTopCategory.visibility = View.INVISIBLE
            }


            //verticalScrollOption
            if(scrollY + (binding.homeTopCategory.bottom - binding.homeTopCategory.top) > binding.homeResOptionScroll.top){
                //val anim = AnimationUtils.loadAnimation(context, R.anim.home_option_anime)
                //binding.homeResOptionScroll.startAnimation(anim)
                binding.homeResOptionScroll.translationY = (scrollY-binding.homeResOptionScroll.top).toFloat()
                binding.homeTopCategory.visibility = View.INVISIBLE
            } else{
                binding.homeResOptionScroll.translationY = 0F
            }

            //fab
            binding.homeFloatingBtn.setOnClickListener {
                binding.homeScroll.scrollTo(0,0)
            }

            if(scrollY > binding.homeHorizontalListLayout.top){ //화면이 내려갈 때
                binding.homeFloatingBtn.visibility = View.VISIBLE
            } else{
                binding.homeFloatingBtn.visibility = View.INVISIBLE
                binding.homeFloatingBtn.setOnClickListener {
                    binding.homeScroll.scrollTo(0,0)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
        autoScrollStart2(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
        autoScrollStop2()
    }

//--------------------------------------------------------------------------------------------------------------------------------

    override fun onGetCategorySuccess(getCategoryResponse: GetCategoryResponse) {
        listSize = getCategoryResponse.result.size
        Log.d("리스트크기", "$listSize")

        for(i in 0 until listSize!!){
            incategoryList[i] = getCategoryResponse.result[i].categoryName
            //ncategoryList.add(getCategoryResponse.result[i].categoryName)
            Log.d("배열", "$incategoryList")
        }

        //topCategory
        var topCategoryArrayList = arrayListOf(
            categoryList(R.drawable.ic_category_1, incategoryList[0]),
            categoryList(R.drawable.ic_category_2, incategoryList[1]),
            categoryList(R.drawable.ic_category_3, incategoryList[2]),
            categoryList(R.drawable.ic_category_4, incategoryList[3]),
            categoryList(R.drawable.ic_category_5, incategoryList[4]),
            categoryList(R.drawable.ic_category_6, incategoryList[5]),
            categoryList(R.drawable.ic_category_7, incategoryList[6]),
            categoryList(R.drawable.ic_category_8, incategoryList[7]),
            categoryList(R.drawable.ic_category_9, incategoryList[8]),
            categoryList(R.drawable.ic_category_10, incategoryList[9]),

            categoryList(R.drawable.ic_category_11, incategoryList[10]),
            categoryList(R.drawable.ic_category_12, incategoryList[11]),
            categoryList(R.drawable.ic_category_13, incategoryList[12]),
            categoryList(R.drawable.ic_category_14, incategoryList[13]),
            categoryList(R.drawable.ic_category_15, incategoryList[14]),
            categoryList(R.drawable.ic_category_16, incategoryList[15]),
            categoryList(R.drawable.ic_category_17, incategoryList[16]),
            categoryList(R.drawable.ic_category_18, incategoryList[17]),
            categoryList(R.drawable.ic_category_19, incategoryList[18]),
            categoryList(R.drawable.ic_category_20, incategoryList[19]),

            categoryList(R.drawable.ic_category_21, incategoryList[20]),
            categoryList(R.drawable.ic_category_22, incategoryList[21]),
            categoryList(R.drawable.ic_category_23, incategoryList[22]),
            categoryList(R.drawable.ic_category_24, incategoryList[23]),
            categoryList(R.drawable.ic_category_25, incategoryList[24]),
            categoryList(R.drawable.ic_category_26, incategoryList[25])
        )
        binding.homeTopCategoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeTopCategoryList.setHasFixedSize(true)
        binding.homeTopCategoryList.adapter = TopCategoryAdapter(topCategoryArrayList)

        //Category
        val categoryArrayList = arrayListOf(
            categoryList(R.drawable.ic_category_1, incategoryList[0]),
            categoryList(R.drawable.ic_category_2, incategoryList[1]),
            categoryList(R.drawable.ic_category_3, incategoryList[2]),
            categoryList(R.drawable.ic_category_4, incategoryList[3]),
            categoryList(R.drawable.ic_category_5, incategoryList[4]),
            categoryList(R.drawable.ic_category_6, incategoryList[5]),
            categoryList(R.drawable.ic_category_7, incategoryList[6]),
            categoryList(R.drawable.ic_category_8, incategoryList[7]),
            categoryList(R.drawable.ic_category_9, incategoryList[8]),
            categoryList(R.drawable.ic_category_10, incategoryList[9]),

            categoryList(R.drawable.ic_category_11, incategoryList[10]),
            categoryList(R.drawable.ic_category_12, incategoryList[11]),
            categoryList(R.drawable.ic_category_13, incategoryList[12]),
            categoryList(R.drawable.ic_category_14, incategoryList[13]),
            categoryList(R.drawable.ic_category_15, incategoryList[14]),
            categoryList(R.drawable.ic_category_16, incategoryList[15]),
            categoryList(R.drawable.ic_category_17, incategoryList[16]),
            categoryList(R.drawable.ic_category_18, incategoryList[17]),
            categoryList(R.drawable.ic_category_19, incategoryList[18]),
            categoryList(R.drawable.ic_category_20, incategoryList[19]),

            categoryList(R.drawable.ic_category_21, incategoryList[20]),
            categoryList(R.drawable.ic_category_22, incategoryList[21]),
            categoryList(R.drawable.ic_category_23, incategoryList[22]),
            categoryList(R.drawable.ic_category_24, incategoryList[23]),
            categoryList(R.drawable.ic_category_25, incategoryList[24]),
            categoryList(R.drawable.ic_category_26, incategoryList[25])
        )
        binding.homeCategoryScroll.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeCategoryScroll.setHasFixedSize(true)
        binding.homeCategoryScroll.adapter = CategoryAdapter(categoryArrayList)
    }
    override fun onGetCategoryFailure(message: String) {
        Log.d("오류", "오류: $message")
    }

    override fun onGetLocationSuccess(getLocationResponse: GetLocationResponse) {
        var code = getLocationResponse.code
        var address = getLocationResponse.result.addressName
        Log.d("위치", "$code, $address")
        check = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)


        if(code==1000 && check!=null){
            binding.homeTopLocationPresent.visibility = View.VISIBLE
            binding.homeTopLocationPresent.text = address

        } else if(code==2001 || check==null){
            //현재 좌표값 기반으로 세팅
            binding.homeTopLocationPresent.text = "주소를 입력하세요"
        }
    }
    override fun onGetLocationFailure(message: String) {
        Log.d("오류", "오류: $message")
    }

    override fun onGetFranResSuccess(getFranResResponse: GetFranResResponse) {
        var hz_name_1 = getFranResResponse.result[0].resName
        var hz_rate_1 = getFranResResponse.result[0].starPoint
        var hz_review_1 = getFranResResponse.result[0].reviewCount
        var hz_distance_1 = getFranResResponse.result[0].distance
        var hz_delivery_1 = getFranResResponse.result[0].minDeliveryFee
        var hz_img_1 = getFranResResponse.result[0].resImageUrl
        var hz_time_1 = getFranResResponse.result[0].deliveryTime
        var hz_id_1 = getFranResResponse.result[0].restaurantId

        var hz_name_2 = getFranResResponse.result[1].resName
        var hz_rate_2 = getFranResResponse.result[1].starPoint
        var hz_review_2 = getFranResResponse.result[1].reviewCount
        var hz_distance_2 = getFranResResponse.result[1].distance
        var hz_delivery_2 = getFranResResponse.result[1].minDeliveryFee
        var hz_img_2 = getFranResResponse.result[1].resImageUrl
        var hz_time_2 = getFranResResponse.result[1].deliveryTime
        var hz_id_2 = getFranResResponse.result[1].restaurantId

        Log.d("가로 리스트", "$hz_name_1, $hz_rate_1, $hz_review_1, $hz_distance_1, $hz_delivery_1, $hz_img_1, $hz_time_1, $hz_id_1")
        Log.d("가로 리스트", "$hz_name_2, $hz_rate_2, $hz_review_2, $hz_distance_2, $hz_delivery_2, $hz_img_2, $hz_time_2, $hz_id_2")

        val horizontalResArrayList = arrayListOf<Result>(
            Result(hz_delivery_1!!,hz_distance_1!!, hz_delivery_1!!, hz_img_1!!, hz_name_1!!, hz_id_1!!, hz_review_1!!, hz_rate_1!! ),
            Result(hz_delivery_2!!,hz_distance_2!!, hz_delivery_2!!, hz_img_2!!, hz_name_2!!, hz_id_2!!, hz_review_2!!, hz_rate_2!! )

            //HorizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000),
            //HorizontalResList(R.drawable.ic_cp_logo,"응급실국물떡볶이 다산점", 5.0, 365, 0.2, 3000),
            //HorizontalResList(R.drawable.ic_cp_logo,"학교종이땡떙땡", 4.8, 656, 0.2, 3000),
            //HorizontalResList(R.drawable.ic_cp_logo,"걸작떡볶이치킨 남양주", 4.7, 274, 0.9, 3000),
            //HorizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000)
        )
        binding.homeHorizontalList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeHorizontalList.setHasFixedSize(true)
        binding.homeHorizontalList.adapter = HorizonalResListAdapter(requireContext(), horizontalResArrayList)


    }

    override fun onGetFranResFailure(message: String) {
        Log.d("오류", "오류: $message")
    }


}
