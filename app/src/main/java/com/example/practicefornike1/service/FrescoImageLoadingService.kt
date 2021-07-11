package com.example.practicefornike1.service

import com.example.practicefornike1.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingService:ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView){
            imageView.setImageURI(imageUrl)
        }
    }
}