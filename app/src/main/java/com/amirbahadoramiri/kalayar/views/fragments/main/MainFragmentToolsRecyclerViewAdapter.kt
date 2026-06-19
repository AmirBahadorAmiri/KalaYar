package com.amirbahadoramiri.kalayar.views.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R

class MainFragmentToolsRecyclerViewAdapter(var dataList: MutableList<ToolsModel>) :
    RecyclerView.Adapter<MainFragmentToolsRecyclerViewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_tools_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = dataList.size


    inner class MainHolder : RecyclerView.ViewHolder {

        val maincard: CardView
        val imageView: AppCompatImageView
        val textView: AppCompatTextView

        constructor(itemView: View) : super(itemView) {
            maincard = itemView.findViewById(R.id.maincard)
            imageView = itemView.findViewById(R.id.imageView)
            textView = itemView.findViewById(R.id.textView)
        }

        fun bind(position: Int) {
            maincard.setOnClickListener(dataList[absoluteAdapterPosition].listener)
            imageView.setImageResource(dataList[absoluteAdapterPosition].drawable)
            textView.setText(dataList[absoluteAdapterPosition].title)
        }

    }

}
