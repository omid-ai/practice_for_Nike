package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Comments
import io.reactivex.Single

interface CommentDataSource {

    fun getComment(productId:Int): Single<List<Comments>>

    fun addComment(): Single<Comments>
}