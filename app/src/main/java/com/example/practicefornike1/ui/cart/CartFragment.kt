package com.example.practicefornike1.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.R
import com.example.practicefornike1.adapter.CartItemAdapter
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeCompletableObserver
import com.example.practicefornike1.common.NikeFragment
import com.example.practicefornike1.data.CartItem
import com.example.practicefornike1.databinding.FragmentCartBinding
import com.example.practicefornike1.service.ImageLoadingService
import com.example.practicefornike1.ui.auth.AuthenticationActivity
import com.example.practicefornike1.ui.auth.LoginFragment
import com.example.practicefornike1.ui.product.ProductDetailActivity
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment:NikeFragment(),CartItemAdapter.CartItemViewCallBacks {

    lateinit var binding:FragmentCartBinding
    val viewModel:CartViewModel by viewModel()
    val imageLoadingService:ImageLoadingService by inject()
    var adapter:CartItemAdapter?=null
    val compositeDisposable=CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartRv.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

        viewModel.cartItemsLiveData.observe(this.viewLifecycleOwner){
            adapter= CartItemAdapter(it as MutableList<CartItem>,imageLoadingService,this)
            binding.cartRv.adapter=adapter
        }

        viewModel.purchaseDetailLiveData.observe(this.viewLifecycleOwner){purchaseDetail->
            Timber.i(purchaseDetail.toString())
            adapter?.let {
                it.purchaseDetail=purchaseDetail
                it.notifyItemChanged(it.cartItems.size)
            }
        }

        viewModel.emptyStateLiveData.observe(this.viewLifecycleOwner){
//            val emptyStateRootView=view.findViewById<View>(R.id.emptyStateRootView)
            val emptyState=showEmptyState(R.layout.layout_empty_state)
            if (it.mustShow){
                emptyState?.let {view->
                    val emptyStateMessageTv=view.findViewById<TextView>(R.id.emptyStateMessageTv)
                    val emptyStateCtaBtn=view.findViewById<MaterialButton>(R.id.emptyStateCtaBtn)
                    emptyStateMessageTv.text=getString(it.messageResId)
                    emptyStateCtaBtn.visibility=if (it.mustShowCallToaActionBtn)View.VISIBLE else View.GONE
                    emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
                    }
                }
            }else
                emptyState?.visibility=View.GONE
        }

//        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
//            if (it.mustShow) {
//                val emptyState = showEmptyState(R.layout.layout_empty_state)
//
//                emptyState?.let { view ->
//                    view.emptyStateMessageTv.text = getString(it.messageResId)
//                    view.emptyStateCtaBtn.visibility =
//                        if (it.mustShowCallToActionButton) View.VISIBLE else View.GONE
//                    view.emptyStateCtaBtn.setOnClickListener {
//                        startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
//                    }
//                }
//            } else
//                emptyStateRootView?.visibility = View.GONE
//        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onIncreaseBtnClicked(cartItem: CartItem) {
        viewModel.increaseItemInCart(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter?.increaseCount(cartItem)
                }
            })
    }

    override fun onDecreaseBtnClicked(cartItem: CartItem) {
        viewModel.decreaseItemInCart(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter?.decreaseCount(cartItem)
                }
            })
    }

    override fun onRemoveBtnClicked(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter?.removeItem(cartItem)
                }
            })
    }

    override fun onProductImageClicked(cartItem: CartItem) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,cartItem.product)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}