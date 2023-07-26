package com.zexceed.tokoku.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
import com.zexceed.tokoku.repository.Resource
import com.zexceed.tokoku.repository.TokokuRepository
import com.zexceed.tokoku.ui.CartViewModel
import com.zexceed.tokoku.ui.InvoiceActivity
import com.zexceed.tokoku.ui.InvoiceActivity.Companion.TAG_INVOICE_CART
import com.zexceed.tokoku.ui.InvoiceActivity.Companion.TAG_INVOICE_TOTALPRICE
import com.zexceed.tokoku.util.AuthPreferences
import com.zexceed.tokoku.util.Constants.API_BASE_URL
import com.zexceed.tokoku.util.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var viewModel: CartViewModel
    private lateinit var homeViewModel: HomeViewModel
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

        val repository = TokokuRepository()
        val factory = ViewModelFactory.getInstance(repository)

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            CartViewModel::class.java)

        homeViewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]

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
        homeViewModel.products.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    product = result.data.products

                    mAdapter.submitList(product)
                    binding.rvProduct.apply {
                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        setHasFixedSize(true)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun Fragment.setActivityTitle(@StringRes id: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(id)
    }

}