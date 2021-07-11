package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Banner
import io.reactivex.Single

interface BannerDataSource {

    fun getBanner(): Single<List<Banner>>
}