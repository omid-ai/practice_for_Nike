package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Comments
import io.reactivex.Single

interface CommentRepository {

    fun getComment(productId:Int):Single<List<Comments>>

    fun addComment():Single<Comments>
}