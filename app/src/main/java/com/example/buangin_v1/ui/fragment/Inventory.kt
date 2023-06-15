package com.example.buangin_v1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.buangin_v1.R
import com.google.android.material.tabs.TabLayout

class Inventory : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)


        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(Plastik(), "Plastik")
        adapter.addFragment(Plastik(), "Kertas")
        adapter.addFragment(Plastik(), "Logam")
        adapter.addFragment(Plastik(), "Kaca")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    internal class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        fun getFragment(position: Int): Fragment? {
            return if (position < fragmentList.size) {
                fragmentList[position]
            } else {
                null
            }
        }
    }


}