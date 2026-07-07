package com.amirbahadoramiri.kalayar.presentation.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.MainFragmentToolsItemBinding

class MainFragmentPageRecyclerViewAdapter(var dataList: MutableList<PageModel>) : RecyclerView.Adapter<MainFragmentPageRecyclerViewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(MainFragmentToolsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: MainHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int = dataList.size


    inner class MainHolder : RecyclerView.ViewHolder {
        val binding: MainFragmentToolsItemBinding
        constructor(binding: MainFragmentToolsItemBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bind() {
            binding.page = dataList[absoluteAdapterPosition]
            binding.maincard.setOnClickListener(dataList[absoluteAdapterPosition].listener)
        }

    }

}
