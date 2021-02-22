package com.example.jub_jub_android.entity.singleton

import android.content.Context
import android.util.Log
import com.example.jub_jub_android.data.local.MyEquipmentDB
import com.example.jub_jub_android.entity.dataclass.MyEquipment
import com.example.jub_jub_android.entity.dataclass.response.MyEquipmentDetailInfo
import com.example.jub_jub_android.ui.util.MyUtil

object MyEquipmentListManager {

    private lateinit var myEquipmentDB : MyEquipmentDB

    private var dividedShowList = ArrayList<ArrayList<MyEquipment>>()

    fun setDataList(context: Context, dataList: ArrayList<MyEquipmentDetailInfo>){
        myEquipmentDB = MyEquipmentDB.getInstance(context)!!
        val thread = Thread(Runnable {
            myEquipmentDB.myEquipmentDAO().clear()
            for(i in 0 until dataList.size){
//            constructor(name: String, category: String, count: Int, image: String, status: String) : this(0, name, category, count, image, status)
                var data = dataList[i].equipment

                myEquipmentDB.myEquipmentDAO().insert(
                    MyEquipment(
                        data.name,
                        data.content,
                        data.count,
                        data.img_equipment,
                        getStatus(dataList[i])))
                //Log.d("TestLog_MyEqManager", "$i = ${MyUtil().convertFileToBase64(data.img_equipment)!!}")
            }

            Log.d("TestLog_MyEqManager", "데이터 입력 완료")
        })

        thread.start()

        try {
            thread.join()
        } catch (e:InterruptedException){Log.d("TestLog_MyEqManager", "데이터 입력 에러 발생 ${e.message}")}

        processShowList("")
    }

    private fun getStatus(data: MyEquipmentDetailInfo): String {
        return if(data.isReturn!!){
            "반납"
        }
        else{
            when(data.equipmentEnum){
                "ROLE_Waiting" -> "대기"
                "ROLE_Accept" -> "대여"
                "ROLE_Reject" -> "거절"
                //대여, 반납, 연체 추가
                else -> "?"
            }
        }


    }

    fun processShowList(key: String){
        var dataList = ArrayList<MyEquipment>()

        var r = Runnable {

            if(key == "전체" || key == ""){
                dataList = myEquipmentDB.myEquipmentDAO().getAll() as ArrayList<MyEquipment>
                Log.d("TestLog", " rent withoutkey ${dataList.size}")
            }
            else {
                dataList = myEquipmentDB.myEquipmentDAO().search("%$key%") as ArrayList<MyEquipment>
                Log.d("TestLog", " rent $key = ${dataList.size}")
            }

            dividedShowList.clear()
            var page = 0
            var cnt = 0
            dividedShowList.add(ArrayList())

            for (i in 0 until dataList.size) {
                if (cnt == 5) {
                    dividedShowList.add(ArrayList())
                    cnt = 0
                    page++
                }
                dividedShowList[page].add(dataList[i])
                cnt++
            }
        }

        val thread = Thread(r)
        thread.start()

        try {
            thread.join()
        } catch (e : InterruptedException){ }
    }

    fun getShowList(): ArrayList<ArrayList<MyEquipment>> {
        return dividedShowList
    }
}