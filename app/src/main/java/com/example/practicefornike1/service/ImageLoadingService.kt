package com.example.practicefornike1.service

import com.example.practicefornike1.view.NikeImageView

interface ImageLoadingService {

    fun load( imageView:NikeImageView, imageUrl:String)
}