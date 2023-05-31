package com.example.buangin_v1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.buangin_v1.R
import com.example.buangin_v1.ui.OnBoardingData

class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList: List<OnBoardingData>):
    PagerAdapter() {
    override fun getCount(): Int {
       return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.content_onboarding, null)
        val imageView : ImageView
        val judul : TextView
        val deskripsi : TextView

        imageView = view.findViewById(R.id.logo)
        judul = view.findViewById(R.id.slide2)
        deskripsi = view.findViewById(R.id.subSlide2)

        imageView.setImageResource(onBoardingDataList[position].image)
        judul.text = onBoardingDataList[position].judul
        deskripsi.text = onBoardingDataList[position].deskripsi

        container.addView(view)
        return view
    }
}