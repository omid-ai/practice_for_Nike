package com.example.practicefornike1.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Banner
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.SORT_LATEST
import com.example.practicefornike1.data.repository.BannerRepository
import com.example.practicefornike1.data.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel( productRepository: ProductRepository, bannerRepository: BannerRepository):NikeViewModel() {

    val productsLiveData=MutableLiveData<List<Product>>()
    val bannerLiveData=MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value=true

        bannerRepository.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value=t
                }
            })

        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value=t
                }
            })
    }
}