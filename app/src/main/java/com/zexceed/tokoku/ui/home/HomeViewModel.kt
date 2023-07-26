package com.zexceed.tokoku.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.tokoku.models.CartEntity
import com.zexceed.tokoku.models.remote.response.products.ProductsResponse
import com.zexceed.tokoku.repository.Resource
import com.zexceed.tokoku.repository.TokokuRepository

class HomeViewModel(
    private val repository: TokokuRepository
) : ViewModel() {

    private lateinit var _products: LiveData<Resource<ProductsResponse>>
    val products get() = _products

    init {
        getProducts()
    }

    private fun getProducts(query: String = "") {
        _products = repository.getProduct(query).asLiveData()
    }

//    private val mRepository: TokokuRepository = TokokuRepository(application)
//
//    fun getAllCart(): LiveData<List<CartEntity>> = mRepository.getAllCart()
//
//    fun getCartById(id: Int): LiveData<CartEntity> = mRepository.getCartById(id)
//
//    fun insertCart(cartEntity: CartEntity) = viewModelScope.launch(Dispatchers.IO) {
//        mRepository.insertCart(cartEntity)
//    }
//
//    fun deleteCartById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
//        mRepository.deleteCartById(id)
//    }
}