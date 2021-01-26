package com.example.jup_jup_android.ui.util

import android.content.Context
import android.view.View
import com.example.jup_jup_android.entity.singleton.ItemStatusListManager
import com.example.jup_jup_android.entity.singleton.RentStatusListManager
import com.example.jup_jup_android.ui.adapter.MyRentList_ViewPagerAdapter
import kotlinx.android.synthetic.main.layout_pageview.view.*

class MyRentList_PageView(var context: Context, var view: View){

    private lateinit var setPageView : SetPageView

    fun initViewPager() {

        val viewPager = view.viewPager

        viewPager.adapter = MyRentList_ViewPagerAdapter (context)

        setPageView = SetPageView(view, viewPager, RentStatusListManager.getShowList() as ArrayList<ArrayList<Any>>)

    }

    fun syncPage(){
        setPageView.syncPage(RentStatusListManager.getShowList().size)
    }

}

