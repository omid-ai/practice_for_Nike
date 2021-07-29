package com.example.practicefornike1.ui.list


import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.R
import com.example.practicefornike1.common.NikeCompletableObserver
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort:Int, val repository: ProductRepository):NikeViewModel() {

    val productListLiveData=MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData=MutableLiveData<Int>()
    val sortTitles= arrayOf(R.string.newest,R.string.popular,R.string.highPrice,R.string.lowPrice)

    init {
        getProduct()
        selectedSortTitleLiveData.value=sortTitles[sort]
    }

    fun getProduct(){
        progressBarLiveData.value=true
        repository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productListLiveData.value=t
                }
            })
    }

    fun selectedSortChangedByUser(sort: Int){
        this.sort=sort
        this.selectedSortTitleLiveData.value=sortTitles[sort]
        getProduct()
    }

    fun onFavoriteBtn(product: Product){
        if (product.isFavorite){
            repository.deleteFromProducts(product).subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite=false
                    }
                })
        }else{
            repository.addToFavorites(product).subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite=true
                    }
                })
        }
    }
}