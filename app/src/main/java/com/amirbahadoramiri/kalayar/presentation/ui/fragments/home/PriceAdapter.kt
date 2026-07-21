package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.HomePriceItemBinding
import com.amirbahadoramiri.kalayar.domain.models.PriceItem

class PriceAdapter : RecyclerView.Adapter<PriceAdapter.PriceHolder>() {

    private var dataList: List<PriceItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PriceHolder(
            HomePriceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        holder.binding.item = dataList[position]
        holder.binding.executePendingBindings()
    }

    class PriceHolder(val binding: HomePriceItemBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PriceItem>) {
        dataList = newList
        notifyDataSetChanged()
    }
}
