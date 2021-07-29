package com.example.practicefornike1.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.R
import com.example.practicefornike1.common.formatPrice
import com.example.practicefornike1.common.implementSpringAnimationTrait
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.service.ImageLoadingService
import com.example.practicefornike1.view.NikeImageView

const val VIEW_TYPE_ROUND=0
const val VIEW_TYPE_SMALL=1
const val VIEW_TYPE_LARGE=2

class ProductAdapter(var viewType: Int= VIEW_TYPE_ROUND, val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<ProductAdapter.viewHolder>() {

    var productItemClickListener:ProductItemClickListener?=null
    var products= ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productIv= itemView.findViewById<NikeImageView>(R.id.productIv)!!
        val productName=itemView.findViewById<TextView>(R.id.productNameTv)!!
        val previousPrice=itemView.findViewById<TextView>(R.id.previousPriceTv)!!
        val currentPrice=itemView.findViewById<TextView>(R.id.currentPriceTv)!!
        val favoriteBtn=itemView.findViewById<ImageView>(R.id.favoriteBtn)
        fun bindProduct(product: Product){
            imageLoadingService.load(productIv,product.image)
            productName.text=product.title
            previousPrice.text= formatPrice(product.previous_price)
            previousPrice.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            currentPrice.text= formatPrice(product.price)
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productItemClickListener?.onProductClicked(product)
            }

            if (product.isFavorite){
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            }else{
                favoriteBtn.setImageResource(R.drawable.ic_favorites)
            }

            favoriteBtn.setOnClickListener {
                productItemClickListener?.onFavoriteBtnClicked(product)
                product.isFavorite=!product.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutResId=when(viewType){
            VIEW_TYPE_SMALL->R.layout.item_product_small
            VIEW_TYPE_LARGE->R.layout.item_product_large
            VIEW_TYPE_ROUND->R.layout.item_product
            else->throw IllegalStateException("unKnown ViewType")
        }
        return viewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int =products.size

    interface ProductItemClickListener{
        fun onProductClicked(product: Product)
        fun onFavoriteBtnClicked(product: Product)
    }
}