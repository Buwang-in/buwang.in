package com.example.buangin_v1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.R
import com.example.buangin_v1.data.Sampah


class SampahAdapter(private val sampahList: List<Sampah>) : RecyclerView.Adapter<SampahAdapter.SampahViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return SampahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampahViewHolder, position: Int) {
        val sampahItem = sampahList[position]
        holder.bind(sampahItem)
    }

    override fun getItemCount(): Int {
        return sampahList.size
    }

    inner class SampahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sampahItem: Sampah) {
            itemView.findViewById<TextView>(R.id.card_jenis).text = sampahItem.jenis
            itemView.findViewById<TextView>(R.id.card_harga).text = sampahItem.harga
        }
    }
}