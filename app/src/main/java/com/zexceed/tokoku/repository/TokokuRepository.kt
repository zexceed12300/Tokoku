package com.zexceed.tokoku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.zexceed.tokoku.apiservices.ApiConfig
import com.zexceed.tokoku.models.CartEntity
import com.zexceed.tokoku.models.local.room.CartDao
import com.zexceed.tokoku.models.local.room.CartDatabase
import com.zexceed.tokoku.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TokokuRepository {

//    private val mCartDao: CartDao
//
//    init {
//        val db = CartDatabase.getDatabase(application)
//        mCartDao = db.cartDao()
//    }
//
//    fun getAllCart(): LiveData<List<CartEntity>> = mCartDao.getCart()
//
//    fun getCartById(id: Int): LiveData<CartEntity> = mCartDao.getCartById(id)
//
//    suspend fun insertCart(cartEntity: CartEntity) {
//        mCartDao.insertCart(cartEntity)
//    }
//
//    suspend fun deleteCartById(id: Int) {
//        mCartDao.deleteCartById(id)
//    }

    fun getProduct(query: String) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(Constants.API_BASE_URL).apiService.getProducts(query)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

}