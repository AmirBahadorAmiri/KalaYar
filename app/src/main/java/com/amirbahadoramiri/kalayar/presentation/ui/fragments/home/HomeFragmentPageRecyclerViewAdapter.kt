package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.HomeFragmentPageItemBinding

class HomeFragmentPageRecyclerViewAdapter(var dataList: MutableList<PageModel>) : RecyclerView.Adapter<HomeFragmentPageRecyclerViewAdapter.PageItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PageItemHolder(HomeFragmentPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: PageItemHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int = dataList.size


    inner class PageItemHolder : RecyclerView.ViewHolder {
        val binding: HomeFragmentPageItemBinding
        constructor(binding: HomeFragmentPageItemBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bind() {
            binding.page = dataList[absoluteAdapterPosition]
            binding.maincard.setOnClickListener(dataList[absoluteAdapterPosition].listener)
        }

    }

}
