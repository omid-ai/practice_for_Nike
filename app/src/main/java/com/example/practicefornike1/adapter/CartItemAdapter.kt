package com.example.practicefornike1.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.R
import com.example.practicefornike1.common.formatPrice
import com.example.practicefornike1.data.CartItem
import com.example.practicefornike1.data.PurchaseDetail
import com.example.practicefornike1.service.ImageLoadingService
import com.example.practicefornike1.ui.cart.CartViewModel
import com.example.practicefornike1.view.NikeImageView

const val CART_ITEM_VIEW_TYPE = 0
const val PURCHASE_DETAILS_VIEW_TYPE = 1

class CartItemAdapter(
    val cartItems: MutableList<CartItem>,
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallBacks: CartItemViewCallBacks
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null


    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv = itemView.findViewById<NikeImageView>(R.id.productImageV)
        val productName = itemView.findViewById<TextView>(R.id.productName)
        val currentPriceTv = itemView.findViewById<TextView>(R.id.currentPrice)
        val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPrice)
        val productCount = itemView.findViewById<TextView>(R.id.productCountTv)
        val increaseItemCountIv = itemView.findViewById<ImageView>(R.id.increaseItemCountIv)
        val decreaseItemCountIv = itemView.findViewById<ImageView>(R.id.decreaseItemCountIv)
        val removeFromCart = itemView.findViewById<TextView>(R.id.removeFromCart)
        val changeCountProgressBar = itemView.findViewById<ProgressBar>(R.id.changeCountProgressBar)
        fun bindCart(cartItem: CartItem) {
            imageLoadingService.load(productIv, cartItem.product.image)
            productName.text = cartItem.product.title
            previousPriceTv.text =
                formatPrice(cartItem.product.previous_price + cartItem.product.discount)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(cartItem.product.price)
            productCount.text = cartItem.count.toString()

            increaseItemCountIv.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                changeCountProgressBar.visibility = View.VISIBLE
                productCount.visibility = View.INVISIBLE
                cartItemViewCallBacks.onIncreaseBtnClicked(cartItem)
            }

            decreaseItemCountIv.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    changeCountProgressBar.visibility = View.VISIBLE
                    productCount.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onDecreaseBtnClicked(cartItem)
                }
            }

            changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

            productCount.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            removeFromCart.setOnClickListener {
                cartItemViewCallBacks.onRemoveBtnClicked(cartItem)
            }

            productIv.setOnClickListener {
                cartItemViewCallBacks.onProductImageClicked(cartItem)
            }
        }
    }

    class PurchaseDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val totalPriceTv = itemView.findViewById<TextView>(R.id.tvTotalPrice)
        val payablePriceTv = itemView.findViewById<TextView>(R.id.tvPayablePrice)
        val shippingCostTv = itemView.findViewById<TextView>(R.id.tvShippingCost)
        fun bind(totalPrice: Int, payablePrice: Int, shippingCost: Int) {
            totalPriceTv.text = formatPrice(totalPrice)
            payablePriceTv.text = formatPrice(payablePrice)
            shippingCostTv.text = formatPrice(shippingCost)
        }
    }

    interface CartItemViewCallBacks {
        fun onIncreaseBtnClicked(cartItem: CartItem)
        fun onDecreaseBtnClicked(cartItem: CartItem)
        fun onRemoveBtnClicked(cartItem: CartItem)
        fun onProductImageClicked(cartItem: CartItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CART_ITEM_VIEW_TYPE)
            return CartViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            )
        else
            return PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_detail, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder)
            holder.bindCart(cartItems[position])
        else if (holder is PurchaseDetailViewHolder)
            purchaseDetail?.let {
                holder.bind(it.total_price, it.payable_price, it.shipping_cost)
            }
    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)
            return PURCHASE_DETAILS_VIEW_TYPE
        else
            return CART_ITEM_VIEW_TYPE
    }

    fun removeItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }
}