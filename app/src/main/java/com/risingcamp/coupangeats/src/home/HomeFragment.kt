package com.risingcamp.coupangeats.src.home

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.ApplicationClass
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.src.home.location.LocationActivity
import com.risingcamp.coupangeats.src.home.models.getCategory.GetCategoryResponse
import com.risingcamp.coupangeats.src.home.models.getFranRes.FranResult
import com.risingcamp.coupangeats.src.home.models.getFranRes.GetFranResResponse
import com.risingcamp.coupangeats.src.home.models.getLocation.GetLocationResponse
import com.risingcamp.coupangeats.src.home.models.getMidBanner.GetMidBannerResponse
import com.risingcamp.coupangeats.src.home.models.getMidBanner.MidBannerResult
import com.risingcamp.coupangeats.src.home.models.getNewRes.GetNewResResponse
import com.risingcamp.coupangeats.src.home.models.getNewRes.Result
import com.risingcamp.coupangeats.src.home.models.getResList.GetResListResponse
import com.risingcamp.coupangeats.src.home.models.getResList.ResListResult
import com.risingcamp.coupangeats.src.home.models.getTopBanner.GetTopBannerResponse
import com.risingcamp.coupangeats.src.home.models.getTopBanner.TopBannerResult
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

    var incategoryList = mutableListOf<String>()

    var top_banner_id_1 : Int? = null
    var top_banner_img_1 : String? = null
    var top_banner_id_2 : Int? = null
    var top_banner_img_2 : String? = null
    var top_banner_id_3 : Int? = null
    var top_banner_img_3 : String? = null
    var top_banner_id_4 : Int? = null
    var top_banner_img_4 : String? = null
    var top_banner_id_5 : Int? = null
    var top_banner_img_5 : String? = null

    var mid_banner_id_1 : Int? = null
    var mid_banner_img_1 : String? = null
    var mid_banner_id_2 : Int? = null
    var mid_banner_img_2 : String? = null

    var check : String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLocation()

        setTopBanner()
        setPage()

        setTopCategory()
        setCategory()

        setHorizontalResList()
        setHorizontalResList2()

        setMidBanner()

        setResListOption()
        setResList()

        setFabBtn()

        binding.homeResOptionScroll.translationZ = 1.0f
    }

    //?????? ??????
    fun setLocation(){
        var userId = ApplicationClass.sSharedPreferences.getInt("UserId", 0)

        if(check != null) {
            HomeService(this).tryGetLocation(userId, true)
        } else{
            binding.homeTopLocationPresent.text = "???????????????"
        }

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

    //??????
    fun setTopBanner(){
        HomeService(this).tryGetTopBanner()
    }

    fun setMidBanner(){
        HomeService(this).tryGetMidBanner()
    }

    //?????? ??????
    fun setPage() {
        binding.homeTopBannerVp.setCurrentItem((Int.MAX_VALUE / 2)-3, true)
        binding.homeMidBannerVp.setCurrentItem((Int.MAX_VALUE/2), true)
    }

    private fun autoScrollStart(intervalTime : Long){
        homeTopBannerHandler.removeMessages(0) // ?????? ????????? ???????????? ???????????? ?????? ?????????
        homeTopBannerHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime?????? ???????????? ????????? ??????
    }
    private fun autoScrollStart2(intervalTime: Long){
        homeMidBannerHandler.removeMessages(0) // ?????? ????????? ???????????? ???????????? ?????? ?????????
        homeMidBannerHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime?????? ???????????? ????????? ??????
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

    //????????????
    @RequiresApi(Build.VERSION_CODES.M)
    fun setTopCategory(){
        HomeService(this).tryGetCategory()
        Log.d("??????", "$incategoryList")

        binding.homeTopCategory.visibility = View.INVISIBLE
        setScrollListener()
    }

    fun setCategory(){
        HomeService(this).tryGetCategory()
        Log.d("??????", "$incategoryList")
    }

    //?????? ????????? (???????????????+?????????)
    fun setHorizontalResList(){
        HomeService(this).tryGetFranRes()
    }

    fun setHorizontalResList2(){
        HomeService(this).tryGetNewRes()
    }

    //?????? ????????? ??????
    @RequiresApi(Build.VERSION_CODES.M)
    fun setResListOption(){

        setScrollListener()

        //?????? ??????
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

    //?????? ?????????
    fun setResList(){
        HomeService(this).tryGetResList()
    }

    /*
    fun setEndlessList(){

        binding.homeResList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //????????? ????????? ????????? ???????????? position
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                //???????????? ????????? ???????????? ??? ?????? -1 (???????????? ??????????????? ??? ??????????????? ??????)
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                Log.d("position", "$lastVisibleItemPosition")

                //???????????? ?????? ??????????????? ??????(?????????:1, ?????????:-1)
                if(!binding.homeResList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount){

                }

            }
        })
    }
     */

    //FAB??????
    @RequiresApi(Build.VERSION_CODES.M)
    fun setFabBtn(){
        binding.homeFloatingBtn.visibility = View.INVISIBLE
        setScrollListener()

    }

    //????????? ?????????
    @RequiresApi(Build.VERSION_CODES.M)
    fun setScrollListener(){
        binding.homeScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("?????????", "$scrollY, $oldScrollY")

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

            if(scrollY > binding.homeHorizontalListLayout.top){ //????????? ????????? ???
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
        Log.d("???????????????", "$listSize")

        for(i in 0 until listSize!!){
            incategoryList.add(getCategoryResponse.result[i].categoryName)
        }
        Log.d("??????", "$incategoryList")

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
        Log.d("??????", "??????: $message")
    }

    override fun onGetLocationSuccess(getLocationResponse: GetLocationResponse) {
        var code = getLocationResponse.code
        var address = getLocationResponse.result.addressName
        Log.d("??????", "$code, $address")
        check = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        if(check==null){
            binding.homeTopLocationPresent.text = "???????????????"
        } else{
            if(code==1000 && check!=null){
                binding.homeTopLocationPresent.visibility = View.VISIBLE
                binding.homeTopLocationPresent.text = address
            }
        }



    }
    override fun onGetLocationFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


    override fun onGetFranResSuccess(getFranResResponse: GetFranResResponse) {
        val franResList = getFranResResponse.result
        Log.d("?????? ?????????", "$franResList")
        binding.homeHorizontalList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeHorizontalList.setHasFixedSize(true)
        binding.homeHorizontalList.adapter = HorizontalResListAdapter(requireContext(), franResList)
    }
    override fun onGetFranResFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


    override fun onGetNewResSuccess(getNewResResponse: GetNewResResponse) {
        var newResList = getNewResResponse.result
        Log.d("?????? ?????????2", "$newResList")
        binding.homeHorizontalList2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeHorizontalList2.setHasFixedSize(true)
        binding.homeHorizontalList2.adapter = HorizontalResList2Adapter(requireContext(), newResList)
    }
    override fun onGetNewResFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


    override fun onGetTopBannerSuccess(getTopBannerResponse: GetTopBannerResponse) {
        top_banner_id_1 = getTopBannerResponse.result[0].eventId
        top_banner_img_1 = getTopBannerResponse.result[0].eventImageUrl
        top_banner_id_2 = getTopBannerResponse.result[1].eventId
        top_banner_img_2 = getTopBannerResponse.result[1].eventImageUrl
        top_banner_id_3 = getTopBannerResponse.result[2].eventId
        top_banner_img_3 = getTopBannerResponse.result[2].eventImageUrl
        top_banner_id_4 = getTopBannerResponse.result[3].eventId
        top_banner_img_4 = getTopBannerResponse.result[3].eventImageUrl
        top_banner_id_5 = getTopBannerResponse.result[4].eventId
        top_banner_img_5 = getTopBannerResponse.result[4].eventImageUrl

        Log.d("?????? ??????", "$top_banner_id_1, $top_banner_img_1, $top_banner_id_2, $top_banner_img_2, $top_banner_id_3, $top_banner_img_3, $top_banner_id_4, $top_banner_img_4, $top_banner_id_5, $top_banner_img_5")

        var models = arrayListOf<TopBannerResult>(
            TopBannerResult(top_banner_id_1!!, top_banner_img_1!!),
            TopBannerResult(top_banner_id_2!!, top_banner_img_2!!),
            TopBannerResult(top_banner_id_3!!, top_banner_img_3!!),
            TopBannerResult(top_banner_id_4!!, top_banner_img_4!!),
            TopBannerResult(top_banner_id_5!!, top_banner_img_5!!)
        )
        var count = models.size
        adapter = TopBannerAdapter(models, requireContext())
        binding.homeTopBannerVp.adapter = adapter

        //???????????? ???????????? ??????
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
    override fun onGetTopBannerFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


    override fun onGetMidBannerSuccess(getMidBannerResponse: GetMidBannerResponse) {
        mid_banner_id_1 = getMidBannerResponse.result[0].eventId
        mid_banner_img_1 = getMidBannerResponse.result[0].eventImageUrl
        mid_banner_id_2 = getMidBannerResponse.result[1].eventId
        mid_banner_img_2 = getMidBannerResponse.result[1].eventImageUrl

        var models = arrayListOf<MidBannerResult>(
            MidBannerResult(mid_banner_id_1!!, mid_banner_img_1!!),
            MidBannerResult(mid_banner_id_2!!, mid_banner_img_2!!)
        )
        var count = models.size
        adapter2 = MidBannerAdapter(models, requireContext())
        binding.homeMidBannerVp.adapter = adapter2

        //???????????? ???????????? ??????
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
    override fun onGetMidBannerFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


    override fun onGetResListSuccess(getResListResponse: GetResListResponse) {
        var resList = getResListResponse.result
        Log.d("?????????", "$resList")

        binding.homeResList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.homeResList.setHasFixedSize(true)
        binding.homeResList.adapter = ResListAdapter(resList)
    }
    override fun onGetResListFailure(message: String) {
        Log.d("??????", "??????: $message")
    }


}
