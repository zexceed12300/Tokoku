package com.zexceed.tokoku.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.tokoku.databinding.ItemProductBinding
import com.zexceed.tokoku.models.remote.response.products.ProductsProductResponse
import com.zexceed.tokoku.ui.CartViewModel
import com.zexceed.tokoku.util.Constants.createImageProgress

class ProductAdapter(
    private val onClickQtyIncrease: (ProductsProductResponse) -> Unit,
    private val onClickQtyDecrease: (ProductsProductResponse) -> Unit,
    private val onClickBtnCart: (ProductsProductResponse) -> Unit,
    val viewModel: CartViewModel
): ListAdapter<ProductsProductResponse, ProductAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductsProductResponse) {

            binding.apply {
                tvTitle.text = data.title
                tvPrice.text = "$ " + data.price.toString()
                tvRating.text = data.rating.toString()
                Glide.with(itemView.context)
                    .load(data.thumbnail)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)

                tvQty.text = viewModel.getQty(data.id).toString()
                btnIncrease.setOnClickListener {
                    onClickQtyIncrease(data)
                    tvQty.text = viewModel.getQty(data.id).toString()
                }
                btnDecrease.setOnClickListener {
                    onClickQtyDecrease(data)
                    tvQty.text = viewModel.getQty(data.id).toString()
                }

                btnCart.setOnClickListener {
                    onClickBtnCart(data)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ProductsProductResponse> = object: DiffUtil.ItemCallback<ProductsProductResponse>() {
            override fun areItemsTheSame(oldItem: ProductsProductResponse, newItem: ProductsProductResponse): Boolean {
                return newItem.id == oldItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ProductsProductResponse, newItem: ProductsProductResponse): Boolean {
                return oldItem == newItem
            }

        }
    }
}