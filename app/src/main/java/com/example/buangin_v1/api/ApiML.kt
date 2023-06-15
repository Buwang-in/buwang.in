package com.example.buangin_v1.api


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.io.File
import java.io.FileOutputStream
import java.util.HashMap

class ApiML {
   companion object{
       suspend fun performObjectDetection(imageData: ByteArray): Pair<Bitmap, Map<String, Int>> =
           withContext(Dispatchers.IO) {
               val url = "https://buwang-in-ml-api-dwkesfpftq-as.a.run.app/object-to-img" // Ganti dengan URL server FastAPI Anda
               val client = OkHttpClient()

               // Membuat permintaan multipart
               val requestBody = MultipartBody.Builder()
                   .setType(MultipartBody.FORM)
                   .addFormDataPart(
                       "file",
                       "image.jpg",
                       imageData.toRequestBody("image/jpeg".toMediaTypeOrNull())
                   )
                   .build()

               // Membuat permintaan HTTP POST
               val request = Request.Builder()
                   .url(url)
                   .post(requestBody)
                   .build()

               // Mengirim permintaan dan mendapatkan respons
               val response = client.newCall(request).execute()

               if (response.isSuccessful) {
                   val responseBody = response.body
                   val imageBytes = responseBody?.bytes()
                   val labelCountsHeader = response.header("x-label-counts")
                   Log.d(labelCountsHeader, "Ini label nama dan jumlah")

                   if (imageBytes != null) {
                       val resultBitmap =
                           BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                       val trashItems = parseMapFromString(labelCountsHeader)
                       Log.d(trashItems.toString(), "Aku TrashITem")
                       Pair(resultBitmap, trashItems)
                   } else {
                       throw IllegalStateException("Failed to decode image from response body.")
                   }

               } else {
                   throw IllegalStateException("Request failed with code ${response.code}")
               }

           }

       private fun parseMapFromString(mapString: String?): Map<String, Int> {
           val mapType = object : TypeToken<Map<String, Int>>() {}.type
           return Gson().fromJson(mapString, mapType) ?: emptyMap()
       }


       fun createDetectedLabelCountsBundle(detectedLabelCounts: Map<String, Int>): Bundle {
           val bundle = Bundle()
           bundle.putSerializable("detected_label_counts", HashMap(detectedLabelCounts))
           return bundle
       }

   }
}