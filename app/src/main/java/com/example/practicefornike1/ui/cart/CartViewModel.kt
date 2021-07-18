package com.example.practicefornike1.ui.cart

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.R
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.*
import com.example.practicefornike1.data.repository.CartRepository
import io.reactivex.Completable

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrBlank()) {
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.get()
                .asyncNetworkRequest()
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.payable_price, t.shipping_cost, t.total_price)
                        } else
                            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                    }
                })
        } else
            emptyStateLiveData.value =
                EmptyState(true, R.string.cartEmptyStateLoggingRequired, true)
    }

    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
                if (cartItemsLiveData.value!!.isEmpty())
                    emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState)) 
                //2 upper line are different from video!
            }.ignoreElement()
    }

    fun increaseItemInCart(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }
            .ignoreElement()
    }

    fun decreaseItemInCart(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }
            .ignoreElement()
    }

    fun refresh() {
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItemList ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var payablePrice = 0
                var totalPrice = 0
                cartItemList.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.payable_price = payablePrice
                purchaseDetail.total_price = totalPrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}