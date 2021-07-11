package com.example.practicefornike1.data

import com.example.practicefornike1.data.CartItem

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)