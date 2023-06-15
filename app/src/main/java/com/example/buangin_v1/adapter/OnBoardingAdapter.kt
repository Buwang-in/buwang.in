package com.example.buangin_v1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buangin_v1.R
import com.example.buangin_v1.data.OnBoardingData

class OnBoardingAdapter(private val onBoardDatas: List<OnBoardingData>) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>(){
    inner class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val logo = view.findViewById<ImageView>(R.id.logo)
        private val judul = view.findViewById<TextView>(R.id.slide2)
        private val deskripsi = view.findViewById<TextView>(R.id.subSlide2)

        fun bind(onBoardingData: OnBoardingData){
            logo.setImageResource(onBoardingData.image)
            judul.text = onBoardingData.judul
            deskripsi.text = onBoardingData.deskripsi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
       return OnBoardingViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.content_onboarding,
           parent,
               false
           )
       )
    }

    override fun getItemCount(): Int {
        return onBoardDatas.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
       holder.bind(onBoardDatas[position])
    }
}