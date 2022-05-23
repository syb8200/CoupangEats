package com.risingcamp.coupangeats.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.config.BaseFragment
import com.risingcamp.coupangeats.databinding.FragmentHomeBinding

data class categoryList(val categoryImg:Int, val categoryTxt:String)
data class horizontalResList(val img:Int, val name:String, val rate:Double, val comment:Int, val distance:Double, val delivery:Int)

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    lateinit var adapter : TopBannerAdapter
    var currentPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopBanner()
        setPage()
        setCategory()
        setHorizontalResList()

    }

    fun setTopBanner(){
        var models = arrayListOf(R.drawable.ic_cp_logo,R.drawable.ic_cp_logo, R.drawable.ic_cp_logo, R.drawable.ic_cp_logo, R.drawable.ic_cp_logo)
        var count = models.size
        adapter = TopBannerAdapter(models, requireContext())
        binding.homeTopBannerVp.offscreenPageLimit = 3
        binding.homeTopBannerVp.getChildAt(0).overScrollMode=View.OVER_SCROLL_NEVER
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

        val thread = Thread(PagerRunnable())
        thread.start()
    }

    //배너 관련
    fun setPage(){
        binding.homeTopBannerVp.setCurrentItem((Int.MAX_VALUE/2)-3, true)
    }

    inner class PagerRunnable:Runnable{
        override fun run() {
            while (requireActivity()!=null && isAdded){
                Thread.sleep(3000)
                requireActivity().runOnUiThread{
                    binding.homeTopBannerVp.setCurrentItem(currentPosition+1,true)
                }
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
            horizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000),
            horizontalResList(R.drawable.ic_cp_logo,"응급실국물떡볶이 다산점", 5.0, 365, 0.2, 3000),
            horizontalResList(R.drawable.ic_cp_logo,"학교종이땡떙땡", 4.8, 656, 0.2, 3000),
            horizontalResList(R.drawable.ic_cp_logo,"걸작떡볶이치킨 남양주", 4.7, 274, 0.9, 3000),
            horizontalResList(R.drawable.ic_cp_logo,"와이낫 떡볶이 구리점", 4.9, 352, 1.9, 3000)
        )

        binding.homeHorizontalList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeHorizontalList.setHasFixedSize(true)
        binding.homeHorizontalList.adapter = HorizontalResListAdapter(horizontalResArrayList)
    }



}