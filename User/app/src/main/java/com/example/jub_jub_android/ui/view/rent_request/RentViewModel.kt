package com.example.jub_jub_android.ui.view.rent_request

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jub_jub_android.data.remote.NetRetrofit
import com.example.jub_jub_android.databinding.ActivityRentBinding
import com.example.jub_jub_android.entity.dataclass.Equipment
import com.example.jub_jub_android.entity.dataclass.response.EquipmentAllowDTO
import com.example.jub_jub_android.entity.dataclass.response.MyResponse
import com.example.jub_jub_android.entity.singleton.TokenManager
import kotlinx.android.synthetic.main.activity_rent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RentViewModel: ViewModel() {

    var itemAmount = 0
    var rentAmount = 0
    var rentReason = ""
    lateinit var binding : ActivityRentBinding

    fun init(data: Equipment, binding: ActivityRentBinding){
        itemAmount = data.count
        this.binding = binding
    }

    fun rentRequest(context: Context, equipmentAllowDTO: EquipmentAllowDTO, eqName: String) {
        val response: Call<MyResponse> = NetRetrofit.getServiceApi().rentEquipment(TokenManager.getToken(), equipmentAllowDTO, eqName)

        response.enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {

                if(response.isSuccessful && response.body()!!.success)
                    Toast.makeText(context, "대여 신청 완료!", Toast.LENGTH_SHORT).show()
                else{
                    Toast.makeText(context, "${response.body()?.msg}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun plusItem(){
        Log.d("TestLog_RVM", "Plus 클릭, rA = $rentAmount, iA = $itemAmount")
        if(rentAmount < itemAmount){
            rentAmount++
            binding.invalidateAll()
        }
    }

    fun minusItem(){
        Log.d("TestLog_RVM", "Minus 클릭, rA = $rentAmount, iA = $itemAmount")
        if(rentAmount > 1) {
            rentAmount--
            binding.invalidateAll()
        }
    }

}