package com.zexceed.tokoku.models.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zexceed.tokoku.models.CartEntity

@Dao
interface CartDao {
    @Query("select * from cart")
    fun getCart(): LiveData<List<CartEntity>>

    @Query("select * from cart where id = :id")
    fun getCartById(id: Int): LiveData<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Query("delete from cart where id = :id")
    suspend fun deleteCartById(id: Int)
}