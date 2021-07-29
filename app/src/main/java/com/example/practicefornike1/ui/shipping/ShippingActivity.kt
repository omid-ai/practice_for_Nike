package com.example.practicefornike1.ui.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.practicefornike1.R
import com.example.practicefornike1.adapter.CartItemAdapter
import com.example.practicefornike1.common.EXTRA_KET_ID
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.common.openUrlInCustomTab
import com.example.practicefornike1.data.SubmitOrderResult
import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.databinding.ActivityShippingBinding
import com.example.practicefornike1.ui.auth.AuthenticationActivity
import com.example.practicefornike1.ui.checkout.CheckOutActivity
import com.google.android.material.button.MaterialButton
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ShippingActivity : AppCompatActivity() {

    lateinit var binding: ActivityShippingBinding
    val viewModel:ShippingViewModel by viewModel { parametersOf(intent.extras) }
    val compositeDisposable=CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.purchaseDetailsLiveData.observe(this){
            val purchaseDetailView=findViewById<View>(R.id.purchaseDetailView)
            val viewHolder=CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
            viewHolder.bind(it.total_price,it.payable_price,it.shipping_cost)
        }

        fun onBtnClicked()=View.OnClickListener{
            if (!TokenContainer.token.isNullOrEmpty()){
                viewModel.submitOrder(binding.firstNameEt.text.toString(),binding.lastNameEt.text.toString(),binding.phoneNumberEt.text.toString()
                ,binding.postalCodeEt.text.toString(),binding.addressEt.text.toString(),
                if (it.id==R.id.onlinePay)PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD).asyncNetworkRequest()
                    .subscribe(object :NikeSingleObserver<SubmitOrderResult>(compositeDisposable){
                        override fun onSuccess(t: SubmitOrderResult) {
                            if (t.bank_gateway_url.isNotEmpty()){
                                openUrlInCustomTab(this@ShippingActivity,t.bank_gateway_url)
                            }else
                                startActivity(Intent(this@ShippingActivity,CheckOutActivity::class.java).apply {
                                    putExtra(EXTRA_KET_ID,t.order_id)
                                })
                        }
                    })
            }else
                startActivity(Intent(this,AuthenticationActivity::class.java))
        }

        val onlinePayBtn=findViewById<MaterialButton>(R.id.onlinePay)
        onlinePayBtn.setOnClickListener(onBtnClicked())
        binding.codPay.setOnClickListener(onBtnClicked())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}