package com.zexceed.tokoku.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zexceed.tokoku.models.CartModels
import com.zexceed.tokoku.models.remote.response.products.ProductsProductResponse

class CartViewModel : ViewModel() {
    private val _cart = MutableLiveData<HashMap<Int, CartModels>>()
    val cart: List<CartModels> get() = _cart.value?.values!!.toList()

    private val _totalPrice = MutableLiveData<Int>(0)
    val totalPrice: LiveData<Int> = _totalPrice

    init {
        _cart.value = hashMapOf()
        _totalPrice.value = 0
    }

    fun addProduct(id: Int, product: ProductsProductResponse) {
        _cart.value!![id] = CartModels(
            product.id,
            product.title,
            product.price,
            1,
            product.price
        )
        _totalPrice?.value = _totalPrice?.value?.plus(product.price)
    }

    fun getProductById(id: Int): CartModels? {
        return _cart.value!![id]
    }

    fun isProductExist(id: Int): Boolean {

        if (_cart.value?.contains(id) == true)
            return true
        return false
    }

    fun getQty(id: Int): Int {
        if (isProductExist(id)) {
            return _cart.value!![id]!!.qty
        } else {
            return 0
        }
    }

    fun getTotalPrice(): Int {
        return _totalPrice.value!!
    }

    fun increaseQty(id: Int, product: ProductsProductResponse) {
        Log.d("CartViewModel:::::", "increaseQty: ${cart}")
        if (isProductExist(id)) {
            _cart.value!![id] = CartModels(
                _cart.value!![id]!!.id,
                _cart.value!![id]!!.title,
                product.price,
                _cart.value!![id]!!.qty + 1,
                _cart.value!![id]!!.subTotal + _cart.value!![id]!!.price,
            )
            _totalPrice.value = _totalPrice.value?.plus(product.price)
        } else {
            addProduct(id, product)
        }
    }

    fun decreaseQty(id: Int, product: ProductsProductResponse) {
        Log.d("CartViewModel:::::", "decreaseQty: ${cart}")
        if (isProductExist(id)) {
            if (_cart.value!![id]?.qty == 1) {
                _cart.value?.remove(id)
            } else {
                _cart.value!![id] = CartModels(
                    _cart.value!![id]!!.id,
                    _cart.value!![id]!!.title,
                    product.price,
                    _cart.value!![id]!!.qty - 1,
                    _cart.value!![id]!!.subTotal - _cart.value!![id]!!.price,
                )
            }
            _totalPrice.value = _totalPrice.value?.minus(product.price)
        }
    }
}