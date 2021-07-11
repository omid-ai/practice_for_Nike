package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.service.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun getComment(productId:Int): Single<List<Comments>> =apiService.getComments(productId)

    override fun addComment(): Single<Comments> {
        TODO("Not yet implemented")
    }
}