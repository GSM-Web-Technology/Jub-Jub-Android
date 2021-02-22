package com.example.jub_jub_admin.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jub_jub_admin.R
import com.example.jub_jub_admin.data.remote.NetRetrofit
import com.example.jub_jub_admin.entity.dataclass.Equipment
import com.example.jub_jub_admin.entity.dataclass.response.MyResponse
import com.example.jub_jub_admin.entity.singleton.TokenManager
import com.example.jub_jub_admin.ui.util.MyUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_modify_item.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ModifyEquipmentActivity : AppCompatActivity(){

    var isModify = false
    var isImageSelected = false
    lateinit var imageUri: Uri
    lateinit var data: Equipment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_item)

        if(intent.hasExtra("Data")){
            isModify = true
            data = intent.getSerializableExtra("Data") as Equipment

            Log.d("TestLog_ModifyEquip", "data.image = ${data.image}")

            modifyMode(data)
        }
        init()
    }

    private fun init() {

        imageView_OpenGallery_ModifyItemActivity.setOnClickListener {
            openGallery()
        }
        imageView_BackArrow_ModifyItemActivity.setOnClickListener {
            finish()
        }

        button_Modify_ModifyItemActivity.setOnClickListener {
            if(isModify){
                modifyData()
            } else{
                addData()
            }
        }

    }

    private fun modifyData() {

        //val imagePath = MyUtil.getPathFromBase64(applicationContext, data.image)
        val imageFile = File(data.image)

        //imageView_ItemImage_ModifyActivity.drawable.toBitmap().toString()

        val fileBody: RequestBody = RequestBody.create("image/png".toMediaTypeOrNull(), imageFile.path)
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", "photo.jpg", fileBody)

        val newName = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemName_ModifyItemActivity.text.toString())
        val category = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemCategory_ModifyItemActivity.text.toString())
        val count = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemCount_ModifyItemActivity.text.toString())
        val oldName = RequestBody.create("text/plain".toMediaTypeOrNull(), data.name)

        val response :Call<MyResponse> = NetRetrofit.getServiceApi().modifyEquipment(TokenManager.getToken(), filePart, category,
                count,oldName , newName)

        response.enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.success == true) {
                        Log.d("TestLog_ModifyEquip", "Success!")
                    } else {
                        Log.d("TestLog_ModifyEquip", "isSuc = true / suc = Fal")
                        Log.d("TestLog_ModifyEquip", "${response.body()}")
                        Log.d("TestLog_ModifyEquip", "${response.body()?.msg}")
                    }
                }
            }
            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Log.d("TestLog_ModifyEquip", "Fail!, ${t.message}")
            }

        })
    }

    private fun modifyMode(data: Equipment){
        Picasso.get().load(data.image).into(imageView_ItemImage_ModifyActivity)
        editText_ItemName_ModifyItemActivity.setText(data.name)
        editText_ItemCount_ModifyItemActivity.setText(data.count.toString())
        editText_ItemCategory_ModifyItemActivity.setText(data.category)

        textView_ModifyMode_ModifyItemActivity.text = "수정"
        button_Modify_ModifyItemActivity.text = "수정"

        isImageSelected = true
    }

    private fun openGallery() {
        // 암시적 인텐트로 갤러리 열기
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            imageUri = data?.data!!

            //Uri 제대로 왔는지 테스트하는 코드
            imageView_ItemImage_ModifyActivity.setImageURI(imageUri)
            isImageSelected = true
            val image : Bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, imageUri)
        }
    }


    private fun addData() {
        if(checkEditText()){

            val imagePath = MyUtil.getPathFromUri(applicationContext, imageUri)
            val imageFile = File(imagePath)
            //val requestFile = RequestBody.create(applicationContext.contentResolver.getType(MyUtil.getUriFromBitmap(applicationContext, MyUtil.convertBase64ToBitmap(data.image))!!)!!.toMediaTypeOrNull(), imageFile)
            val imageRequestBody = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
            val imageBody = MultipartBody.Part.createFormData("img_equipment", imageFile.name, imageRequestBody)

            val name = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemName_ModifyItemActivity.text.toString())
            val category = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemCategory_ModifyItemActivity.text.toString())
            val count = RequestBody.create("text/plain".toMediaTypeOrNull(), editText_ItemCount_ModifyItemActivity.text.toString())


//            var file: String? = null
//            var imageFile = MyUtil.uriToFile(imageUri, applicationContext)
//            val filePath: MultipartBody.Part = MyUtil.createMultiPart(imageFile)
//            val addItemData = ModifyItem()
//            Log.d("TestLog", "${addItemData}")

            val response: Call<MyResponse> = NetRetrofit.getServiceApi().addItem(TokenManager.getToken(),imageBody, name, category, count)

            response.enqueue(object : Callback<MyResponse> {
                override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                    Log.d("TestLog_ModifyEQ", "message = ${response.message()} \n")
                    Log.d("TestLog_ModifyEQ", "success = ${response.body()?.success} \n")
                    Log.d("TestLog_ModifyEQ", "msg = ${response.body()?.msg} \n")
                    Log.d("TestLog_ModifyEQ", "code = ${response.body()?.code} \n")
                    if(response.isSuccessful){
                        if(response.body()?.success== true){
                            Toast.makeText(applicationContext, "기자재 등록 완료", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Log.d("TestLog_ModifyEq", "${response.body()?.msg}")
                        }
                    }
                    else{
                        Log.d("TestLog_ModifyEq", "${response.body()?.msg}")
                    }

                }

                override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                    Log.d("TestLog_ModifyEQ", "Fail message = ${t.message} \n")

//                    Log.d("TestLog", "message = ${response.message()} \n")
//                    Log.d("TestLog", "success = ${response.body()?.success} \n")
//                    Log.d("TestLog", "msg = ${response.body()?.msg} \n")
//                    Log.d("TestLog", "code = ${response.body()?.code} \n")
                }

            })
        }
    }

    private fun checkEditText(): Boolean {
        return if(editText_ItemName_ModifyItemActivity.text.toString() == "" || editText_ItemCount_ModifyItemActivity.text.toString() == ""
                || editText_ItemCategory_ModifyItemActivity.text.toString() == "") {
            Toast.makeText(applicationContext, "빈칸을 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            false
        }
        else if(!isImageSelected){
            Toast.makeText(applicationContext, "기자재 사진을 등록해주세요!", Toast.LENGTH_SHORT).show()
            false
        }
        else{
            Log.d("TestLog", "checkEditText 통과")
            true
        }
    }


}