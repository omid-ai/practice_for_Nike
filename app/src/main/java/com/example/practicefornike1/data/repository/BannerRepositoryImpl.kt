package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Banner
import com.example.practicefornike1.data.repository.source.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerDataSource):BannerRepository {
    override fun getBanner(): Single<List<Banner>> =bannerRemoteDataSource.getBanner()
}