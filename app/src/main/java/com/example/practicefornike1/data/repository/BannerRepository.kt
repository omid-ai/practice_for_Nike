package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanner():Single<List<Banner>>
}