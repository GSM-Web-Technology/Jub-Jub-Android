package com.example.jup_jup_android.ui.adapter

import android.content.Context
import android.util.Log
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jup_jup_android.R
import com.example.jup_jup_android.entity.singleton.ItemStatusListManager
import kotlinx.android.synthetic.main.fragment_item_status_list_1.*
import kotlinx.android.synthetic.main.fragment_item_status_list_1.view.*

import java.util.*
class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private var context = context
    private lateinit var layoutInflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return ItemStatusListManager.getDevidedItemStatusList().size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.fragment_item_status_list, null)
        val reserveCal: Calendar = Calendar.getInstance()

        var adapter = ItemStatusList_RecyclerViewAdpater(view.context, position)
        view.recyclerView_ItemStatusList.recyclerView_ItemStatusList.adapter = adapter
        adapter.notifyDataSetChanged()

        //setTextViewsText(view, reserveCal, position)
        container.addView(view)

        return view
    }




}