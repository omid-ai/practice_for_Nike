package com.example.practicefornike1.ui.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.CartRepository
import com.example.practicefornike1.data.repository.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(
    bundle: Bundle,
    val commentRepository: CommentRepository,
    val cartRepository: CartRepository
) : NikeViewModel() {

    val productDetailLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comments>>()

    init {
        progressBarLiveData.value = true
        productDetailLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)

        commentRepository.getComment(productDetailLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comments>>(compositeDisposable) {
                override fun onSuccess(t: List<Comments>) {
                    commentLiveData.value = t
                }
            })
    }

    fun onAddToCartBtn():Completable= cartRepository.addToCart(productDetailLiveData.value!!.id).ignoreElement()
}