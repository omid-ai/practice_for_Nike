package com.example.practicefornike1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.databinding.ItemFavoriteProductBinding
import com.example.practicefornike1.service.ImageLoadingService

class FavoriteListAdapter(val imageLoadingService: ImageLoadingService,val products: MutableList<Product>,
val eventListener: FavoriteListEventListener):
    RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    lateinit var binding: ItemFavoriteProductBinding

    inner class ViewHolder(binding: ItemFavoriteProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bindFavorites(product: Product){
            binding.favoriteProductTv.text=product.title
            imageLoadingService.load(binding.favoriteProductIv,product.image)

            itemView.setOnClickListener {
                eventListener.onItemClicked(product)
            }

            itemView.setOnLongClickListener {
                eventListener.onItemLongClicked(product)
                product.isFavorite=false
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFavorites(products[position])
    }

    override fun getItemCount(): Int = products.size

    interface FavoriteListEventListener{
        fun onItemClicked(product: Product)
        fun onItemLongClicked(product: Product)
    }
}