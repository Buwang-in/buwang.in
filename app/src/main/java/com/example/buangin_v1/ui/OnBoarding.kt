package com.example.buangin_v1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.buangin_v1.R
import com.example.buangin_v1.adapter.OnBoardingViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class OnBoarding : AppCompatActivity() {

    var OnBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null


//    private val sliderOnboardingAdapter: SliderOnboardingAdapter = SliderOnboardingAdapter(
//        listOf(
//            SliderOnboarding(judul1, subJudul1, R.drawable.slide1),
//            SliderOnboarding(judul2, subJudul2, R.drawable.slide2),
//            SliderOnboarding(judul3, subJudul3, R.drawable.slide3),
//        )
//    )
//    private lateinit var sliderOnboardingViewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

    tabLayout = findViewById(R.id.tab)

    //inisialisasi
    val judul1 = getString(R.string.selamat_dat)
    val subJudul1 = getString(R.string.kami_senang)
    val judul2 = getString(R.string.slide2)
    val subJudul2 = getString(R.string.subTitleSlide2)
    val judul3 = getString(R.string.title_slide3)
    val subJudul3 = getString(R.string.sub_title_Slide3)


    val onBoardingData:MutableList<OnBoardingData> = ArrayList()
    onBoardingData.add(OnBoardingData(judul1,subJudul1,R.drawable.slide1))
    onBoardingData.add(OnBoardingData(judul2,subJudul2,R.drawable.slide2))
    onBoardingData.add(OnBoardingData(judul3,subJudul3,R.drawable.slide3))

    setOnBoardingViewPagerAdapter(onBoardingData)
}

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager);
        OnBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = OnBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }
}