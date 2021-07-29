package com.example.practicefornike1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.common.convertDpToPixel
import com.example.practicefornike1.common.formatPrice
import com.example.practicefornike1.data.OrderHistoryItem
import com.example.practicefornike1.databinding.ItemOrderHistoryBinding
import com.example.practicefornike1.view.NikeImageView

class OrderHistoryAdapter(val context: Context,val orderHistoryItems: List<OrderHistoryItem>):
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    lateinit var binding: ItemOrderHistoryBinding
    val layoutParams:LinearLayout.LayoutParams

    init {
        val size= convertDpToPixel(100f,context).toInt()
        val margin= convertDpToPixel(8f,context).toInt()
        layoutParams=LinearLayout.LayoutParams(size,size)
        layoutParams.setMargins(margin,0,margin,0)
    }

    inner class ViewHolder(binding: ItemOrderHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bindOrderHistory(orderHistoryItem: OrderHistoryItem){
            binding.orderHistoryId.text=orderHistoryItem.id.toString()
            binding.orderHistoryPrice.text= formatPrice(orderHistoryItem.payable)
            binding.orderHistoryProductLL.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView=NikeImageView(context)
                imageView.layoutParams=layoutParams
                imageView.setImageURI(it.product.image)
                binding.orderHistoryProductLL.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindOrderHistory(orderHistoryItems[position])
    }

    override fun getItemCount(): Int = orderHistoryItems.size
}