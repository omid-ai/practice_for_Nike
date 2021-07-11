package com.example.practicefornike1.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.adapter.BannerSliderAdapter
import com.example.practicefornike1.adapter.ProductAdapter
import com.example.practicefornike1.adapter.VIEW_TYPE_ROUND
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.common.convertDpToPixel
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.SORT_LATEST
import com.example.practicefornike1.databinding.FragmentHomeBinding
import com.example.practicefornike1.ui.list.ProductListActivity
import com.example.practicefornike1.ui.product.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(),ProductAdapter.ProductItemClickListener {

    private var binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModel()
    private val adapter:ProductAdapter by inject { parametersOf(VIEW_TYPE_ROUND)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.newestProductRv?.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        adapter.productItemClickListener=this

        homeViewModel.productsLiveData.observe(this.viewLifecycleOwner) {
            Timber.i(it.toString())
            adapter.products= it as ArrayList<Product>
            binding?.newestProductRv?.adapter=adapter

        }

        binding?.viewAllProducts?.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        homeViewModel.bannerLiveData.observe(this.viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding?.bannerSliderViewPager?.adapter = bannerSliderAdapter
//            calculateBannerSize()
            binding?.sliderIndicator?.setViewPager2(binding!!.bannerSliderViewPager)
        }

        homeViewModel.progressBarLiveData.observe(this.viewLifecycleOwner) {
            setProgressBarIndicator(it)
        }
    }

//    fun calculateBannerSize() {
//        val bannerHeight =
//            (binding?.bannerSliderViewPager?.measuredWidth!! - convertDpToPixel(
//                32f,
//                requireContext()
//            ) * 173) / 328
//        var layoutParam=binding?.bannerSliderViewPager?.layoutParams
//        layoutParam?.height=bannerHeight.toInt()
//        binding?.bannerSliderViewPager?.layoutParams=layoutParam
//    }

    override fun onResume() {
        super.onResume()
//        calculateBannerSize()
    }

    override fun onProductClicked(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}