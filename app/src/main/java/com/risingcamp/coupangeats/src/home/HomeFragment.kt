package com.risingcamp.coupangeats.src.home

import android.app.Activity
import android.content.ClipData
import android.os.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.ActivityMainBinding
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding
import com.risingcamp.coupangeats.src.MainActivity
import com.risingcamp.coupangeats.src.search.SearchFragment

data class categoryList(val categoryImg:Int, val categoryTxt:String)

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    lateinit var adapter : TopBannerAdapter
    lateinit var adapter2 : MidBannerAdapter

    private var homeTopBannerHandler = HomeTopBannerHandler()
    private var homeMidBannerHandler = HomeMidBannerHandler()

    private val intervalTime = 5000.toLong()

    var currentPosition = 0
    var currentPosition2 = 0

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

        setTopBanner()
        setPage()
        setCategory()
        setHorizontalResList()
        setMidBanner()
        setVerticalResList()
        setEndlessList()
        setFabBtn()

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

    fun setCategory(){
        val categoryArrayList = arrayListOf(
            categoryList(R.drawable.ic_cp_logo, "포장"),
            categoryList(R.drawable.ic_cp_logo, "신규 맛집"),
            categoryList(R.drawable.ic_cp_logo, "1인분"),
            categoryList(R.drawable.ic_cp_logo, "한식"),
            categoryList(R.drawable.ic_cp_logo, "치킨"),
            categoryList(R.drawable.ic_cp_logo, "분식"),
            categoryList(R.drawable.ic_cp_logo, "돈까스"),
            categoryList(R.drawable.ic_cp_logo, "족발/보쌈"),
            categoryList(R.drawable.ic_cp_logo, "찜/탕"),
            categoryList(R.drawable.ic_cp_logo, "구이"),

            categoryList(R.drawable.ic_cp_logo, "피자"),
            categoryList(R.drawable.ic_cp_logo, "중식"),
            categoryList(R.drawable.ic_cp_logo, "일식"),
            categoryList(R.drawable.ic_cp_logo, "회/해물"),
            categoryList(R.drawable.ic_cp_logo, "양식"),
            categoryList(R.drawable.ic_cp_logo, "커피/차"),
            categoryList(R.drawable.ic_cp_logo, "디저트"),
            categoryList(R.drawable.ic_cp_logo, "간식"),
            categoryList(R.drawable.ic_cp_logo, "아시안"),
            categoryList(R.drawable.ic_cp_logo, "샌드위치"),

            categoryList(R.drawable.ic_cp_logo, "샐러드"),
            categoryList(R.drawable.ic_cp_logo, "버거"),
            categoryList(R.drawable.ic_cp_logo, "멕시칸"),
            categoryList(R.drawable.ic_cp_logo, "도시락"),
            categoryList(R.drawable.ic_cp_logo, "죽"),
            categoryList(R.drawable.ic_cp_logo, "프랜차이즈")
        )
        binding.homeCategoryScroll.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeCategoryScroll.setHasFixedSize(true)
        binding.homeCategoryScroll.adapter = CategoryAdapter(categoryArrayList)
    }

    fun setHorizontalResList(){
        val horizontalResArrayList = arrayListOf(
            HorizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000),
            HorizontalResList(R.drawable.ic_cp_logo,"응급실국물떡볶이 다산점", 5.0, 365, 0.2, 3000),
            HorizontalResList(R.drawable.ic_cp_logo,"학교종이땡떙땡", 4.8, 656, 0.2, 3000),
            HorizontalResList(R.drawable.ic_cp_logo,"걸작떡볶이치킨 남양주", 4.7, 274, 0.9, 3000),
            HorizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000)
        )
        binding.homeHorizontalList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeHorizontalList.setHasFixedSize(true)
        binding.homeHorizontalList.adapter = HorizonalResListAdapter(horizontalResArrayList)
    }

    fun setVerticalResList(){

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

    fun setData(){
        dtoList = intent
    }




    @RequiresApi(Build.VERSION_CODES.M)
    fun setFabBtn(){
        binding.homeFloatingBtn.visibility = View.INVISIBLE

        binding.homeScroll.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("스크롤", "$scrollY, $oldScrollY")

            binding.homeFloatingBtn.setOnClickListener {
                binding.homeScroll.scrollTo(0,0)
            }

            if(scrollY > binding.homeHorizontalListLayout.top){ //화면이 내려갈 때
                binding.homeFloatingBtn.visibility = View.VISIBLE

            } else{ //화면이 올라갈 때
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


}
