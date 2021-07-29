package com.example.practicefornike1.ui.shipping

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.data.PurchaseDetail
import com.example.practicefornike1.data.SubmitOrderResult
import com.example.practicefornike1.data.repository.OrderRepository
import io.reactivex.Single

const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(val orderRepository: OrderRepository, bundle: Bundle) : NikeViewModel() {

    val purchaseDetailsLiveData = MutableLiveData<PurchaseDetail>()

    init {
        purchaseDetailsLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }

    fun submitOrder(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        paymentMethod: String
    ):Single<SubmitOrderResult> {
        return orderRepository.submitOrders(firstName,lastName,phoneNumber,address,postalCode,paymentMethod)
    }
}