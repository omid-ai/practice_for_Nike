package com.example.practicefornike1.ui.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.data.Banner
import com.example.practicefornike1.databinding.FragmentBannerBinding
import com.example.practicefornike1.service.ImageLoadingService
import org.koin.android.ext.android.inject

class BannerFragment:Fragment() {

    private var binding:FragmentBannerBinding?=null
    private val imageLoadingService:ImageLoadingService by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBannerBinding.inflate(inflater,container,false)
        val imageView=binding!!.root
        val banner=requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?:throw IllegalStateException("this is null!")
        imageLoadingService.load(imageView,banner.image)
        return imageView
    }


    companion object{
        fun newInstance(banner: Banner):BannerFragment{
            return BannerFragment().apply {
                arguments=Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA,banner)
                }
            }
        }
    }
}