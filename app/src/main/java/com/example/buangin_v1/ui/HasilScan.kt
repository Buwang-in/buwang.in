package com.example.buangin_v1.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.MainActivity
import com.example.buangin_v1.R
import com.example.buangin_v1.adapter.ListArtikelAdapter
import com.example.buangin_v1.data.Artikel
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList


class HasilScan : AppCompatActivity() {
    private lateinit var rvArtikel: RecyclerView
    private lateinit var trashItemsTextView: TextView
    private lateinit var hargaTextView: TextView
    private lateinit var jumlahTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_scan)

        supportActionBar?.apply {
            title = "Hasil Scan"
            setDisplayHomeAsUpEnabled(true)
        }



        val detectedImg = intent.getStringExtra("detected_image") ?: ""
        val detectedLabelCountsBundle = intent.getBundleExtra("detected_label_counts")
        val detectedLabelCounts = detectedLabelCountsBundle?.getSerializable("detected_label_counts") as? Map<String, Int>
        val convertedLabelCounts = detectedLabelCounts?.mapValues { it.value.toString() }

        // Muat gambar dari path
        val bitmap = BitmapFactory.decodeFile(detectedImg)
        val imgScan: ImageView = findViewById(R.id.img_hasil)
        trashItemsTextView = findViewById(R.id.jenis_sampah)
        hargaTextView = findViewById(R.id.harga_sampah)
        jumlahTextView = findViewById(R.id.jenis_sampah)

        trashItemsTextView.text = buildDetectedCountText(detectedLabelCounts ?: emptyMap())

        imgScan.setImageBitmap(bitmap)



        rvArtikel = findViewById(R.id.artikel_terkait)
        rvArtikel.setHasFixedSize(true)

        showRecycle()


        val btnSave = findViewById<MaterialButton>(R.id.btn_save)

        btnSave.setOnClickListener {

//            val scanResult = ketScan.text.toString()
            val jenisSampahValue = trashItemsTextView.text.toString()
            val hargaSampahValue = hargaTextView.text.toString()
            saveScanResult(jenisSampahValue, hargaSampahValue)
            Log.d( saveScanResult(jenisSampahValue, hargaSampahValue).toString(),"Ini isi shared preferance")


        }
    }

    private fun saveScanResult(jenisSampahValue: String, hargaSampahValue: String) {
        // Save the scan result, jenis sampah, and harga sampah using SharedPreferences
        val sharedPreferences = getSharedPreferences("ScanResults", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
//        editor.putString("lastScanResult", scanResult)

        val key = UUID.randomUUID().toString() //tambah
        editor.putString(key, jenisSampahValue)
        editor.putString(jenisSampahValue, hargaSampahValue)
//        editor.putString("jenisSampah", jenisSampahValue)
//        editor.putString("hargaSampah", hargaSampahValue)
        editor.apply()

        Toast.makeText(this, "Scan result saved!", Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun buildDetectedCountText(detectedCount: Map<String, Int>): String {
        val stringBuilder = StringBuilder()
        for ((name, count) in detectedCount) {
            stringBuilder.append("$name: $count\n")
        }
        return stringBuilder.toString().trim()
    }

    private fun showRecycle() {
        rvArtikel.layoutManager = LinearLayoutManager(this)

        val list = getListArtikel()
        val adapter = ListArtikelAdapter(list)
        rvArtikel.adapter = adapter
    }

    private fun getListArtikel(): ArrayList<Artikel> {
        val dataJudul = resources.getStringArray(R.array.data_judul)
        val dataSub = resources.getStringArray(R.array.data_sub)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listArtikel = ArrayList<Artikel>()
        for (i in dataJudul.indices) {
            val hero = Artikel(dataJudul[i], dataSub[i], dataPhoto.getResourceId(i, -1))
            listArtikel.add(hero)
        }

        return listArtikel

    }

}





