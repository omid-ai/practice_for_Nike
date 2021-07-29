package com.example.practicefornike1.ui.list

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practicefornike1.R
import com.example.practicefornike1.adapter.ProductAdapter
import com.example.practicefornike1.adapter.VIEW_TYPE_LARGE
import com.example.practicefornike1.adapter.VIEW_TYPE_SMALL
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeActivity
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.databinding.ActivityProductListBinding
import com.example.practicefornike1.ui.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity(),ProductAdapter.ProductItemClickListener {

    lateinit var binding:ActivityProductListBinding
    val adapter:ProductAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    val viewModel:ProductListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_DATA)) }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityProductListBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val gridLayout=GridLayoutManager(this,2)
            binding.productsRv.layoutManager=gridLayout
            adapter.productItemClickListener=this

            viewModel.productListLiveData.observe(this){
                adapter.products= it as ArrayList<Product>
                binding.productsRv.adapter=adapter
            }

            viewModel.selectedSortTitleLiveData.observe(this){
                binding.viewTypeTv.text=getString(it)
            }

            binding.viewTypeChangerBtn.setOnClickListener {
                if (adapter.viewType== VIEW_TYPE_SMALL){
                    adapter.viewType= VIEW_TYPE_LARGE
                    gridLayout.spanCount=1
                    binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                    adapter.notifyDataSetChanged()
                }else{
                    adapter.viewType= VIEW_TYPE_SMALL
                    gridLayout.spanCount=2
                    binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                    adapter.notifyDataSetChanged()
                }
            }

            viewModel.progressBarLiveData.observe(this){
                setProgressBarIndicator(it)
            }

            binding.sortBtn.setOnClickListener {
                val message=MaterialAlertDialogBuilder(this).setSingleChoiceItems(R.array.sort,viewModel.sort
                ) { dialog, whichSortSelected -> viewModel.selectedSortChangedByUser(whichSortSelected) }
                    .setTitle(R.string.sort)
                message.show()
            }
        }

    override fun onProductClicked(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteBtnClicked(product: Product) {
        viewModel.onFavoriteBtn(product)
    }

}
