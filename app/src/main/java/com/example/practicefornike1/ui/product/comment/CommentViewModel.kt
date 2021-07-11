package com.example.practicefornike1.ui.product.comment

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.data.repository.CommentRepository

class CommentViewModel(val commentRepository: CommentRepository,productId:Int):NikeViewModel() {

    val commentsLiveData=MutableLiveData<List<Comments>>()

    init {
        progressBarLiveData.value=true
        commentRepository.getComment(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Comments>>(compositeDisposable){
                override fun onSuccess(t: List<Comments>) {
                    commentsLiveData.value=t
                }
            })
    }

}