package com.example.practicefornike1.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.databinding.FragmentCartBinding

class CartFragment:NikeFragment() {

    var binding:FragmentCartBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCartBinding.inflate(inflater,container,false)
        return binding!!.root
    }
}