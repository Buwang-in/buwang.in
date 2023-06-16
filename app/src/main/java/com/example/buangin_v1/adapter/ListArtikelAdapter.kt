package com.example.buangin_v1.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.data.Artikel
import com.example.buangin_v1.R

class ListArtikelAdapter(private val listArtikel: ArrayList<Artikel>) : RecyclerView.Adapter<ListArtikelAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_artikel)
        val tvJudul: TextView = itemView.findViewById(R.id.judul_artikel)
        val tvSub: TextView = itemView.findViewById(R.id.sub_artikel)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_artikel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listArtikel.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (judul, subJudul, photo) = listArtikel[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvJudul.text = judul
        holder.tvSub.text = subJudul

        holder.tvJudul.ellipsize = TextUtils.TruncateAt.END
        holder.tvJudul.maxLines = 1
        holder.tvSub.ellipsize = TextUtils.TruncateAt.END
        holder.tvSub.maxLines = 1
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Artikel)
    }
}