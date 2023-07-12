package com.zexceed.tokoku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zexceed.tokoku.R
import com.zexceed.tokoku.adapter.CartAdapter
import com.zexceed.tokoku.databinding.ActivityInvoiceBinding
import com.zexceed.tokoku.models.CartModels

class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var mAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setTitle(getString(R.string.invoice))
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@InvoiceActivity, ViewModelProvider.NewInstanceFactory()).get(CartViewModel::class.java)

        mAdapter = CartAdapter {  }

        val listCart = intent.getSerializableExtra(TAG_INVOICE_CART) as? List<CartModels>

        mAdapter.submitList(listCart)
        binding.rvCart.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@InvoiceActivity)
            setHasFixedSize(true)

        }
        binding.tvTotal.text = getString(R.string.price, intent.getStringExtra(TAG_INVOICE_TOTALPRICE))

        binding.btnDone.setOnClickListener {
            intent = Intent(this@InvoiceActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG_INVOICE_CART = "cart"
        const val TAG_INVOICE_TOTALPRICE = "id"
    }
}