package com.example.practicefornike1.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.NikeCompletableObserver
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteListViewModel(val productRepository: ProductRepository):NikeViewModel() {

    val favoritesLiveData=MutableLiveData<List<Product>>()

    init {
        productRepository.getFavorites().asyncNetworkRequest()
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    favoritesLiveData.value=t
                }
            })
    }

    fun deleteFavoriteProduct(product: Product){
        productRepository.deleteFromProducts(product).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("correctly done")
                }
            })
    }
}