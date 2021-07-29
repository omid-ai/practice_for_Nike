package com.example.practicefornike1.common

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.practicefornike1.adapter.ProductAdapter
import com.example.practicefornike1.data.db.AppDatabase
import com.example.practicefornike1.data.repository.*
import com.example.practicefornike1.data.repository.source.*
import com.example.practicefornike1.service.FrescoImageLoadingService
import com.example.practicefornike1.service.ImageLoadingService
import com.example.practicefornike1.service.createApiServiceInstance
import com.example.practicefornike1.ui.auth.AuthViewModel
import com.example.practicefornike1.ui.cart.CartViewModel
import com.example.practicefornike1.ui.checkout.CheckOutViewModel
import com.example.practicefornike1.ui.favorites.FavoriteListViewModel
import com.example.practicefornike1.ui.list.ProductListViewModel
import com.example.practicefornike1.ui.main.HomeViewModel
import com.example.practicefornike1.ui.main.MainViewModel
import com.example.practicefornike1.ui.order.OrderHistoryViewModel
import com.example.practicefornike1.ui.product.ProductDetailActivity
import com.example.practicefornike1.ui.product.ProductDetailViewModel
import com.example.practicefornike1.ui.product.comment.CommentViewModel
import com.example.practicefornike1.ui.shipping.ShippingViewModel
import com.example.practicefornike1.ui.user.UserViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        val myModule = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            single<SharedPreferences> { this@App.getSharedPreferences("app_Setting", MODE_PRIVATE) }
            single { Room.databaseBuilder(this@App,AppDatabase::class.java,"app_db").build() }
            single { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImpl(
                    UserRemoteDataSource(get()),
                    UserLocalDataSource(get())
                )
            }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDatabase>().productDao()
                )
            }
            factory { (viewType: Int) -> ProductAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            factory<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentViewModel(get(), productId) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { (bundle: Bundle) -> ShippingViewModel(get(),bundle) }
            viewModel {(orderId:Int)-> CheckOutViewModel(get(),orderId) }
            viewModel { UserViewModel(get()) }
            viewModel { FavoriteListViewModel(get()) }
            viewModel { OrderHistoryViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

        val userRepository:UserRepository=get()
        userRepository.loadToken()
    }
}