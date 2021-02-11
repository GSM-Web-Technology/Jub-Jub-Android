package com.example.jub_jub_android.ui.util

import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.Window
import com.example.jub_jub_android.R

import kotlinx.android.synthetic.main.layout_alertdialog.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class MyUtil {

    fun convertBase64ToBitmap(base64Code: String): Bitmap{

        val decodedString: ByteArray = Base64.decode(base64Code, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun getLaptopTestImage(context: Context): String {
        val byteStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image_laptop)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val byteArray: ByteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun getMotorTestImage(context: Context): String{
        val byteStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.imageex)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val byteArray: ByteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun processShowList(dataList: ArrayList<Any>, key: String, showList: ArrayList<ArrayList<Any>>){

        showList.clear()
        var page = 0
        var cnt = 0
        showList.add(ArrayList())

        for (i in 0 until dataList.size) {
            if (cnt == 5) {
                showList.add(ArrayList())
                cnt = 0
                page++
            }
            showList[page].add(dataList[i])
            cnt++
        }
    }

    fun makeBaseDialog(context: Context, dialogName: String): Dialog {
        var dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_alertdialog)

        dialog.textView_AlertName_AlertDialogLayout.text = dialogName
        dialog.textView_AlertContent_AlertDialogLayout.text = "정말 $dialogName 하시겠습니까?"

        return dialog

    }

    fun uriToFile(uri: Uri?, context: Context): String {
        val cursor: Cursor = context.contentResolver.query(uri!!, null, null, null)!!
        cursor.moveToNext()
        val path: String = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }

    fun createMultiPart(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        val requestBody: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("files", file.name, requestBody)
    }

}