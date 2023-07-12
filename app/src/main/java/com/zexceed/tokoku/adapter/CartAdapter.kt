package com.zexceed.tokoku.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zexceed.tokoku.R
import com.zexceed.tokoku.databinding.ItemCartBinding
import com.zexceed.tokoku.models.CartModels

class CartAdapter(
    private val onClick: () -> Unit
): ListAdapter<CartModels, CartAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CartModels) {
            binding.apply {
                tvName.text = data.title
                tvPrice.text = itemView.context.getString(R.string.price, data.price.toString())
                tvQty.text = data.qty.toString()
                tvSubtotal.text = itemView.context.getString(R.string.price, data.subTotal.toString())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CartModels> = object: DiffUtil.ItemCallback<CartModels>() {
            override fun areItemsTheSame(oldItem: CartModels, newItem: CartModels): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CartModels, newItem: CartModels): Boolean {
                return oldItem == newItem
            }

        }
    }
}