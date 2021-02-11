package com.example.jub_jub_user.ui.util.PageView

import android.content.Context
import android.view.View
import com.example.jub_jub_user.entity.dataclass.RentRequest
import com.example.jub_jub_user.entity.singleton.RentRequestListManager
import com.example.jub_jub_user.ui.adapter.viewpager.AllowRentRequest_ViewPagerAdapter
import com.example.jub_jub_user.ui.util.SetPageView
import kotlinx.android.synthetic.main.layout_pageview.view.*

class AllowRentRequestList_PageView(var context: Context, var view: View, var  dataList: ArrayList<ArrayList<RentRequest>>) {

    private lateinit var setPageView : SetPageView

    fun initViewPager() {

        val viewPager = view.viewPager

        viewPager.adapter = AllowRentRequest_ViewPagerAdapter(context)

        setPageView = SetPageView(view, viewPager, dataList as ArrayList<ArrayList<Any>>)

    }

    fun syncPage(){
        //Log.d("TestLog", "ISList.size = ${ItemStatusListManager.getShowList().size}")
        setPageView.syncPage(RentRequestListManager.getShowList().size)
    }




}