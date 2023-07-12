package com.zexceed.tokoku.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zexceed.tokoku.R
import com.zexceed.tokoku.adapter.ProductAdapter
import com.zexceed.tokoku.apiservices.ApiConfig
import com.zexceed.tokoku.databinding.FragmentHomeBinding
import com.zexceed.tokoku.models.CartModels
import com.zexceed.tokoku.models.remote.response.products.ProductsProductResponse
import com.zexceed.tokoku.models.remote.response.products.ProductsResponse
import com.zexceed.tokoku.ui.InvoiceActivity.Companion.TAG_INVOICE_CART
import com.zexceed.tokoku.ui.InvoiceActivity.Companion.TAG_INVOICE_TOTALPRICE
import com.zexceed.tokoku.util.AuthPreferences
import com.zexceed.tokoku.util.Constants.API_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var viewModel: CartViewModel
    private lateinit var mAdapter: ProductAdapter
    private lateinit var preferences: AuthPreferences
    private var product: List<ProductsProductResponse> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(CartViewModel::class.java)

        binding.apply {
            mAdapter = ProductAdapter(
                onClickQtyIncrease = {
                    viewModel.increaseQty(it.id, it)
                    btnCheckout.text = getString(R.string.bayar_sekarang, viewModel.getTotalPrice().toString())
                },
                onClickQtyDecrease = {
                    viewModel.decreaseQty(it.id, it)
                    btnCheckout.text = getString(R.string.bayar_sekarang, viewModel.getTotalPrice().toString())

                },
                onClickBtnCart = {
                    val intent = Intent(requireActivity(), InvoiceActivity::class.java)
                    val cart = viewModel.getProductById(it.id)
                    intent.putExtra(TAG_INVOICE_CART, arrayListOf(cart))
                    intent.putExtra(TAG_INVOICE_TOTALPRICE, cart?.subTotal.toString())
                    startActivity(intent)
                },
                viewModel
            )

            setListProducts()

            btnCheckout.text = getString(R.string.bayar_sekarang, viewModel.getTotalPrice().toString())

            etSearch.addTextChangedListener {
                setListProducts()
            }

            btnCheckout.setOnClickListener {
                val intent = Intent(requireActivity(), InvoiceActivity::class.java)

                val cart = viewModel.cart.map {
                    CartModels(
                        it.id,
                        it.title,
                        it.price,
                        it.qty,
                        it.subTotal
                    )
                } as ArrayList

                intent.putExtra(TAG_INVOICE_CART, cart)
                intent.putExtra(TAG_INVOICE_TOTALPRICE, viewModel.getTotalPrice().toString())
                startActivity(intent)
            }
        }
    }

    private fun setListProducts() {
        val req = ApiConfig(API_BASE_URL).apiService.getProducts(binding.etSearch.text.toString())
        req.enqueue(object: Callback<ProductsResponse> {
            override fun onResponse(
                call: Call<ProductsResponse>,
                response: Response<ProductsResponse>
            ) {
                product = response.body()!!.products

                mAdapter.submitList(product)
                binding.rvProduct.apply {
                    adapter = mAdapter
                    layoutManager = LinearLayoutManager(requireActivity())
                    setHasFixedSize(true)
                }
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun Fragment.setActivityTitle(@StringRes id: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(id)
    }

}