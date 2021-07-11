package com.example.practicefornike1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.databinding.ItemCommentBinding

class CommentAdapter(var showBtn:Boolean=false): RecyclerView.Adapter<CommentAdapter.viewHolder>() {

    private lateinit var binding: ItemCommentBinding
    var comment=ArrayList<Comments>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class viewHolder(binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindComment(comments: Comments){
            binding.commentTitleTv.text=comments.title
            binding.commentDateTv.text=comments.date
            binding.commentAuthorDetailTv.text=comments.author.email
            binding.commentContentTv.text=comments.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding= ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindComment(comment[position])
    }

    override fun getItemCount(): Int =if (comment.size>3 && !showBtn)3 else comment.size
}