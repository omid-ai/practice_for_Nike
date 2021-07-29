package com.example.practicefornike1.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.adapter.OrderHistoryAdapter
import com.example.practicefornike1.databinding.ActivityOrderHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderHistoryBinding
    val viewModel:OrderHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.orderHistoryRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        viewModel.orderHistoryLiveData.observe(this){
            binding.orderHistoryRv.adapter=OrderHistoryAdapter(this,it)
        }
    }
}