package com.example.jub_jub_user.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jub_jub_android.R
import com.example.jub_jub_user.entity.dataclass.ItemStatus

class ItemStatusListFragment(itemStatusList: ArrayList<ItemStatus>) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_item_status_list, container, false)

        return view
    }

}