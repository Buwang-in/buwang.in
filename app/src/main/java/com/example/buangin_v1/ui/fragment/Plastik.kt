package com.example.buangin_v1.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.R
import com.example.buangin_v1.adapter.ScanResultAdapter
import com.example.buangin_v1.data.ScanResult


//class Plastik : Fragment() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var jenisTextView: TextView
//    private lateinit var hargaTextView: TextView
//    private lateinit var jumlahTextView: TextView
//    private lateinit var adapter: ScanResultAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_plastik, container, false)
//        //baru
//        recyclerView = view.findViewById(R.id.rv_inventory_sampah)
//        adapter = ScanResultAdapter()
//
//        // Set up RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val sharedPreferences = requireContext().getSharedPreferences("ScanResults", Context.MODE_PRIVATE)
////        val jenisSampah = sharedPreferences.getString("jenisSampah", null)
////        val hargaSampah = sharedPreferences.getString("hargaSampah", null)
//
//        // ini menampilkan semua
////        val allScanResults = mutableListOf<ScanResult>()
//
////         Add the saved data to the RecyclerView adapter
////        if (jenisSampah != null && hargaSampah != null) {
////            val savedScanResult = ScanResult(jenisSampah, hargaSampah)
////            adapter.addScanResult(savedScanResult)
////        }
//
//        // if untuk semua
////        sharedPreferences.all?.forEach { (key, _) ->
////            if (key != "lastScanResult") {
////                val jenisSampah = sharedPreferences.getString("jenisSampah","")
////                val hargaSampah = sharedPreferences.getString("hargaSampah","")
////                val scanResult = ScanResult(jenisSampah as String, hargaSampah as String)
////                allScanResults.add(scanResult)
////            }
////        }
////
////        adapter.addScanResults(allScanResults)
//        sharedPreferences.all?.forEach { (key, _) ->
//            if (key != "lastScanResult") {
//                val jenisSampah = sharedPreferences.getString("jenisSampah", null)
//                val hargaSampah = sharedPreferences.getString("hargaSampah", null)
//                val scanResult = ScanResult(jenisSampah as String, hargaSampah as String)
//                adapter.addScanResult(scanResult)
//            }
//        }
//
//    }
//
//
//}

class Plastik : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScanResultAdapter
    private lateinit var emptyImageView: ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plastik, container, false)
        recyclerView = view.findViewById(R.id.rv_inventory_sampah)
        emptyImageView = view.findViewById(R.id.img_empty)
        adapter = ScanResultAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireContext().getSharedPreferences("ScanResults", Context.MODE_PRIVATE)
        val scanResultsMap = sharedPreferences.all

        if (scanResultsMap.isNullOrEmpty()) {
            recyclerView.visibility = View.GONE
            emptyImageView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyImageView.visibility = View.GONE

            sharedPreferences.all?.forEach { (_, value) ->
                if (value is String) {
                    val scanResultValue = sharedPreferences.getString(value, null)
                    if (scanResultValue != null) {
                        val scanResult = ScanResult(value, scanResultValue)
                        adapter.addScanResult(scanResult)
                    }
                }
            }
        }
    }


}
