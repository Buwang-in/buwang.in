package com.example.buangin_v1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.R
import com.example.buangin_v1.data.Sampah
import com.example.buangin_v1.databinding.ItemLayoutBinding

//class PagerAdapter ( private val daftarSampah: MutableList<Sampah>) : RecyclerView.Adapter<PagerAdapter.SampahViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampahViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
//        return SampahViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SampahViewHolder, position: Int) {
//        val sampah = daftarSampah[position]
//        holder.bind(sampah)
//    }
//
//
//    override fun getItemCount(): Int {
//        return daftarSampah.size
//    }
//
//    inner class SampahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val jenisTextView: TextView = itemView.findViewById(R.id.card_jenis)
//        private val hargaTextView: TextView = itemView.findViewById(R.id.card_harga)
//        private val jumlahTextView: TextView = itemView.findViewById(R.id.card_jml)
//
//        fun bind(sampah: Sampah) {
//            jenisTextView.text = sampah.jenis
//            hargaTextView.text = sampah.harga
//            jumlahTextView.text = sampah.jumlah
//        }
//
//
//    }
//}


class PagerAdapter : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {
    private var data: List<Sampah> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Sampah>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Sampah) {
            // Bind data to views in the item layout
            val jenis = itemView.findViewById<TextView>(R.id.card_jenis)
            val harga = itemView.findViewById<TextView>(R.id.card_harga)
            jenis.text = item.jenis
            harga.text = item.harga
        }
    }
}
