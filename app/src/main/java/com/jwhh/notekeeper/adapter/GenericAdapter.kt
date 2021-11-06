package com.jwhh.notekeeper.adapter

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jwhh.notekeeper.utils.ClickEnum
import com.jwhh.notekeeper.utils.OnListItemViewClickListener


class GenericAdapter<T>(
    @LayoutRes val layoutId: Int,
    var type: Int,
    val itemClick: OnListItemViewClickListener<T>,
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )

        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: GenericViewHolder<T>,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            val itemViewModel = data[position]
            holder.bind(itemViewModel, type)
            holder.itemView.apply {
                setOnClickListener {
                        itemClick.onClickItem(itemViewModel, ClickEnum.ONE,holder.adapterPosition)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    class GenericViewHolder<T>(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T, F: Int) {
            try {
                binding.setVariable(F, itemViewModel)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    var data: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value)


}