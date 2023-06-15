package com.example.buangin_v1.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.buangin_v1.ui.HasilScan
import com.example.buangin_v1.R
import com.example.buangin_v1.api.ApiML.Companion.createDetectedLabelCountsBundle
import com.example.buangin_v1.api.ApiML.Companion.performObjectDetection
import com.example.buangin_v1.ui.ScanFailed
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Home : Fragment() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var btn_scan: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_scan = view.findViewById(R.id.btn_scan)

        btn_scan.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
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
                Toast.makeText(requireContext(), "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
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
                        val result = performObjectDetection(imageData)
                        val detectedBitmap = result.first
                        val detectedLabelCounts = result.second

                        // Simpan gambar ke penyimpanan lokal
                        val imagePath = saveBitmapLocally(detectedBitmap)

                        if (detectedLabelCounts.isNullOrEmpty()) {
                            val intent = Intent(activity, ScanFailed::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(activity, HasilScan::class.java)
                            intent.putExtra("detected_image", imagePath)
                            val detectedLabelCountsBundle = createDetectedLabelCountsBundle(detectedLabelCounts)
                            intent.putExtra("detected_label_counts", detectedLabelCountsBundle)
                            startActivity(intent)
                        }

//                        val intent = Intent(activity, HasilScan::class.java)
//                        intent.putExtra("detected_image", imagePath)
//                        val detectedLabelCountsBundle = createDetectedLabelCountsBundle(detectedLabelCounts)
//                        intent.putExtra("detected_label_counts", detectedLabelCountsBundle)
//                        startActivity(intent)

                    } catch (e: Exception) {
                        val intent = Intent(activity, ScanFailed::class.java)
                        startActivity(intent)
                        Log.d("Error", "Kegagalan: ${e.message}")
                    }
                }
            }
        }


    }
    private fun saveBitmapLocally(bitmap: Bitmap): String {
        val fileName = "detected_image.jpg" // Nama file yang diinginkan
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, fileName)

        // Simpan bitmap ke file
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }

        return imageFile.absolutePath
    }
}