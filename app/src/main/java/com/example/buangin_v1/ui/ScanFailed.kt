package com.example.buangin_v1.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.buangin_v1.MainActivity
import com.example.buangin_v1.R
import com.example.buangin_v1.api.ApiML
import com.example.buangin_v1.response.DetecResponse
import com.example.buangin_v1.ui.fragment.Home
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ScanFailed : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var btn_scan_again: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_failed)

        btn_scan_again = findViewById(R.id.materialButton)
        btn_scan_again.setOnClickListener {
            if (checkCameraPermission()) {
                // Izin kamera telah diberikan, buka kamera di sini
                openCamera()
            } else {
                // Izin kamera belum diberikan, minta izin kamera
                requestCameraPermission()
            }
        }
    }
    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional, jika Anda ingin menutup HasilScan Activity setelah berpindah ke MainActivity
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap: Bitmap? = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                // Mengirim gambar hasil deteksi sebagai byte array melalui intent
                val byteArray = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                val imageData = byteArray.toByteArray()

                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val result = ApiML.performObjectDetection(imageData)
                        val detectedBitmap = result.first
                        val detectedLabelCounts = result.second

                        // Simpan gambar ke penyimpanan lokal
                        val imagePath = saveBitmapLocally(detectedBitmap)

                        if (detectedLabelCounts.isNullOrEmpty()) {
                            val intent = Intent(this@ScanFailed, ScanFailed::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@ScanFailed, HasilScan::class.java)
                            intent.putExtra("detected_image", imagePath)
                            val detectedLabelCountsBundle =
                                ApiML.createDetectedLabelCountsBundle(detectedLabelCounts)
                            intent.putExtra("detected_label_counts", detectedLabelCountsBundle)
                            startActivity(intent)
                        }

                    } catch (e: Exception) {
                        val intent = Intent(this@ScanFailed, ScanFailed::class.java)
                        startActivity(intent)
                        Log.d("Error", "Kegagalan: ${e.message}")
                    }
                }
            }
        }


    }
    private fun saveBitmapLocally(bitmap: Bitmap): String {
        val fileName = "detected_image.jpg" // Nama file yang diinginkan
        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, fileName)

        // Simpan bitmap ke file
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }

        return imageFile.absolutePath
    }
}