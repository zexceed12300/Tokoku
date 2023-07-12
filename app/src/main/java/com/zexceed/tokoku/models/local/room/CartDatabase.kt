package com.zexceed.tokoku.models.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase {
            if (INSTANCE == null) {
                synchronized(CartDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CartDatabase::class.java,
                        "cart_database"
                    ).build()
                }
            }

            return INSTANCE as CartDatabase
        }
    }
}