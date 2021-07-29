package com.example.practicefornike1.ui.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicefornike1.common.EXTRA_KET_ID
import com.example.practicefornike1.common.formatPrice
import com.example.practicefornike1.databinding.ActivityCheckOutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckOutBinding
    val viewModel:CheckOutViewModel by viewModel {
        val uri:Uri?=intent.data
        if (uri!=null){
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        }else{
            parametersOf(intent.extras!!.getInt(EXTRA_KET_ID))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkoutLiveData.observe(this){
            binding.orderPriceTv.text= formatPrice(it.payable_price)
            binding.orderStatusTv.text=it.payment_status
            binding.checkoutTitleTv.text=if (it.purchase_success)"پرداخت با موفقیت انجام شد" else "پرداخت ناموفق"
        }
    }
}