package com.zexceed.tokoku.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zexceed.tokoku.models.CartEntity
import com.zexceed.tokoku.repository.StylishRepository

class HomeViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: StylishRepository = StylishRepository(application)

    fun getAllCart(): LiveData<List<CartEntity>> = mRepository.getAllCart()
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