package com.example.recyclerviewexample.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewexample.ui.model.ItemGif
import com.example.recyclerviewexample.databinding.ItemGifBinding

class GifViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGifBinding.bind(view)

    // this function is called for each item of recyclerview
    fun render(itemGif: ItemGif, onItemGifClickListener: (ItemGif) -> Unit) {

        binding.tvGif.text = itemGif.title
        binding.tvRealName.text = itemGif.gifId

        Glide.with(binding.ivGif.context).load(itemGif.images.original.url)
            .into(binding.ivGif)

        itemView.setOnClickListener {
            onItemGifClickListener(itemGif)
        }
    }
}