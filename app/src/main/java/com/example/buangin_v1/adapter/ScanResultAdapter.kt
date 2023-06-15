
package com.example.buangin_v1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.R
import com.example.buangin_v1.data.ScanResult

//class ScanResultAdapter : RecyclerView.Adapter<ScanResultAdapter.ScanResultViewHolder>() {
//    private val scanResults: MutableList<ScanResult> = mutableListOf()
//
//    fun addScanResult(scanResult: ScanResult) {
//        scanResults.add(scanResult)
//        notifyDataSetChanged()
//    }
//
////fun addScanResults(newScanResults: List<ScanResult>) {
////    scanResults.addAll(newScanResults)
////    notifyDataSetChanged()
////}
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
//        return ScanResultViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ScanResultViewHolder, position: Int) {
//        val scanResult = scanResults[position]
//        holder.bind(scanResult)
//    }
//
//    override fun getItemCount(): Int = scanResults.size
//
//    inner class ScanResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val txtJenisSampah: TextView = itemView.findViewById(R.id.card_jenis)
//        private val txtHargaSampah: TextView = itemView.findViewById(R.id.card_harga)
//
//        fun bind(scanResult: ScanResult) {
//            txtJenisSampah.text = scanResult.jenisSampah
//            txtHargaSampah.text = scanResult.hargaSampah
//        }
//    }
//}




class ScanResultAdapter: RecyclerView.Adapter<ScanResultAdapter.ScanResultViewHolder>() {
    private val scanResults: MutableList<ScanResult> = mutableListOf()


    fun addScanResult(scanResult: ScanResult) {
//        scanResults.add(scanResult)
//        notifyDataSetChanged()
        if (!scanResults.contains(scanResult)) {
            scanResults.add(scanResult)
            notifyDataSetChanged()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ScanResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScanResultViewHolder, position: Int) {
        val scanResult = scanResults[position]
        holder.bind(scanResult)
    }

    override fun getItemCount(): Int = scanResults.size

    inner class ScanResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtJenisSampah: TextView = itemView.findViewById(R.id.card_jenis)
        private val txtHargaSampah: TextView = itemView.findViewById(R.id.card_harga)
        private val deleteButton: ImageView = itemView.findViewById(R.id.btn_delete)


        fun bind(scanResult: ScanResult) {
            txtJenisSampah.text = scanResult.jenisSampah
            txtHargaSampah.text = scanResult.hargaSampah

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sharedPreferences = itemView.context.getSharedPreferences("ScanResults", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val scanResult = scanResults[position]

                    // Remove data from SharedPreferences
                    editor.remove(scanResult.jenisSampah)
                    editor.apply()

                    scanResults.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}
