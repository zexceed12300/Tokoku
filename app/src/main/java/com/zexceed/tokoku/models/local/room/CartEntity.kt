package com.zexceed.tokoku.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity (
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "qty")
    val qty: Int,

    @ColumnInfo(name = "subTotal")
    val subTotal: Int
)
