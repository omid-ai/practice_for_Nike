package com.example.practicefornike1.ui.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefornike1.R
import com.example.practicefornike1.adapter.FavoriteListAdapter
import com.example.practicefornike1.common.EXTRA_KEY_DATA
import com.example.practicefornike1.common.NikeActivity
import com.example.practicefornike1.common.NikeCompletableObserver
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.databinding.ActivityFavoriteListBinding
import com.example.practicefornike1.ui.product.ProductDetailActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListActivity : NikeActivity(),FavoriteListAdapter.FavoriteListEventListener {

    lateinit var binding: ActivityFavoriteListBinding
    val viewModel:FavoriteListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoritesRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)



        viewModel.favoritesLiveData.observe(this){
            binding.favoritesRv.adapter=FavoriteListAdapter(get(), it as MutableList<Product>,this)
            if (it.isEmpty())
                showEmptyState(R.layout.view_default_empty_state)
        }

        binding.infoBtn.setOnClickListener {
            showSnackBar(getString(R.string.favorites_help_message))
        }
    }

    override fun onItemClicked(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onItemLongClicked(product: Product) {
        viewModel.deleteFavoriteProduct(product)
    }
}