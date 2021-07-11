package com.example.practicefornike1.ui.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.R
import com.example.practicefornike1.adapter.CommentAdapter
import com.example.practicefornike1.common.EXTRA_KET_ID
import com.example.practicefornike1.common.NikeActivity
import com.example.practicefornike1.common.NikeCompletableObserver
import com.example.practicefornike1.common.formatPrice
import com.example.practicefornike1.data.Comments
import com.example.practicefornike1.databinding.ActivityProductDetailBinding
import com.example.practicefornike1.service.ImageLoadingService
import com.example.practicefornike1.ui.product.comment.CommentListActivity
import com.example.practicefornike1.view.observableScrollView.ObservableScrollViewCallbacks
import com.example.practicefornike1.view.observableScrollView.ScrollState
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : NikeActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    val viewModel:ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService:ImageLoadingService by inject()
    val commentAdapter=CommentAdapter()
    val compositeDisposable=CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.commentRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        binding.ivProduct.post {
            val height=binding.ivProduct.measuredHeight
            binding.observableScrollView.addScrollViewCallbacks(object :ObservableScrollViewCallbacks{
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    binding.toolBar.alpha=scrollY.toFloat()/ height.toFloat()
                    binding.ivProduct.translationY=scrollY.toFloat()/2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

        binding.addToCartBtn.setOnClickListener {
            viewModel.onAddToCartBtn().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }
                })
        }

        viewModel.progressBarLiveData.observe(this){
            setProgressBarIndicator(it)
        }

        viewModel.productDetailLiveData.observe(this){
            imageLoadingService.load(binding.ivProduct,it.image)
            binding.tvProductName.text=it.title
            binding.productTitleTv.text=it.title
            binding.previousPrice.text= formatPrice(it.previous_price)
            binding.previousPrice.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            binding.currentPrice.text= formatPrice(it.price)
        }

        viewModel.commentLiveData.observe(this){
            commentAdapter.comment= it as ArrayList<Comments>
            binding.commentRv.adapter=commentAdapter
            if (it.size>3){
                binding.viewAllCommentsBtn.visibility=View.VISIBLE
                binding.viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this,CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KET_ID,viewModel.productDetailLiveData.value!!.id)
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}