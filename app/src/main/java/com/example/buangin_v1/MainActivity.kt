package com.example.buangin_v1

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.buangin_v1.ui.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = Home()
        val inventory = Plastik()
        val riwayat = Riwayat()
        val akun = Akun()

        setCurrentFragment(homeFragment)

        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    setCurrentFragment(homeFragment)
                    Log.i(TAG,"Home")
                }
                R.id.navigation_inventori -> {
                    setCurrentFragment(inventory)
                    Log.i(TAG,"Inventori")
                }
                R.id.navigation_history -> {
                    setCurrentFragment(riwayat)
                    Log.i(TAG,"Riwayat")
                }
                R.id.navigation_akun -> {
                    setCurrentFragment(akun)
                    Log.i(TAG,"Akun")
                }
            }
            val colorStateList = ContextCompat.getColorStateList(this, R.color.color_icon)
            bottomNavigationView.itemIconTintList = colorStateList
            bottomNavigationView.itemTextColor = colorStateList

            true
        }
    }
    //Back keluar aplikasi
    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.f1_wrapper,fragment)
            commit()
        }


}