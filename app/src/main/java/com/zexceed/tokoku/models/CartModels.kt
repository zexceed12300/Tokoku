package com.zexceed.tokoku.models

import java.io.Serializable

data class CartModels (
    val id: Int,
    val title: String,
    val price: Int,
    val qty: Int,
    val subTotal: Int
) : Serializable
