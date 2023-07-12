package com.zexceed.tokoku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.zexceed.tokoku.models.CartEntity
import com.zexceed.tokoku.models.local.room.CartDao
import com.zexceed.tokoku.models.local.room.CartDatabase

class StylishRepository(application: Application) {

    private val mCartDao: CartDao

    init {
        val db = CartDatabase.getDatabase(application)
        mCartDao = db.cartDao()
    }

    fun getAllCart(): LiveData<List<CartEntity>> = mCartDao.getCart()

    fun getCartById(id: Int): LiveData<CartEntity> = mCartDao.getCartById(id)

    suspend fun insertCart(cartEntity: CartEntity) {
        mCartDao.insertCart(cartEntity)
    }

    suspend fun deleteCartById(id: Int) {
        mCartDao.deleteCartById(id)
    }

}