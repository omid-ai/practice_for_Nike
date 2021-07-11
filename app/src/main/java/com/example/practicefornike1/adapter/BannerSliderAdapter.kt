package com.example.practicefornike1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practicefornike1.data.Banner
import com.example.practicefornike1.ui.banner.BannerFragment

class BannerSliderAdapter(fragment: Fragment, private val banners:List<Banner>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =banners.size

    override fun createFragment(position: Int): Fragment =BannerFragment.newInstance(banners[position])
}