package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.data.repository.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(private val remoteDataSource: CommentDataSource):CommentRepository {

    override fun getComment(productId:Int): Single<List<Comments>> =remoteDataSource.getComment(productId)

    override fun addComment(): Single<Comments> {
        TODO("Not yet implemented")
    }
}