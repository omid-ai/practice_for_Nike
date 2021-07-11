package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Banner
import com.example.practicefornike1.service.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDataSource {
    override fun getBanner(): Single<List<Banner>> = apiService.getBanner()
}