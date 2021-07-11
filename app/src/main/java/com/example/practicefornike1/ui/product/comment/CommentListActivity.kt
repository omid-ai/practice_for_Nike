package com.example.practicefornike1.ui.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.adapter.CommentAdapter
import com.example.practicefornike1.common.EXTRA_KET_ID
import com.example.practicefornike1.common.NikeActivity
import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.databinding.ActivityCommentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

    private lateinit var binding:ActivityCommentListBinding
    val viewModel:CommentViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KET_ID)) }
    val commentAdapter=CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentsRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        viewModel.progressBarLiveData.observe(this){
            setProgressBarIndicator(it)
        }

        binding.commentsToolBar.onBackBtnPressedCallback= View.OnClickListener {
            finish()
        }

        viewModel.commentsLiveData.observe(this){
            commentAdapter.comment= it as ArrayList<Comments>
            commentAdapter.showBtn=true
            binding.commentsRv.adapter=commentAdapter
        }
    }
}