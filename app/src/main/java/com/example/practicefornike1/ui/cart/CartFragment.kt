package com.example.practicefornike1.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment:NikeFragment() {

    var binding:FragmentCartBinding?=null
    val viewModel:CartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCartBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartItemsLiveData.observe(this.viewLifecycleOwner){
            Timber.i(it.toString())
        }

        viewModel.purchaseDetailLiveData.observe(this.viewLifecycleOwner){
            Timber.i(it.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }
}