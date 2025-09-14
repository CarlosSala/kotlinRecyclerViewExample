package com.example.recyclerviewexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.ui.model.ItemGif

class GifAdapter(
    private var itemGifList: List<ItemGif>,
    private val onClickListener: (ItemGif) -> Unit
) :
    RecyclerView.Adapter<GifViewHolder>() {

    fun updateList(newList: List<ItemGif>) {

        val gifDiffUtil = GifDiffUtil(oldList = itemGifList, newList = newList)
        val result = DiffUtil.calculateDiff(gifDiffUtil)
        itemGifList = newList
        result.dispatchUpdatesTo(this)
    }

    // return a item to ViewHolder by each object of superhero
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return GifViewHolder(layoutInflater.inflate(R.layout.item_gif, parent, false))
    }

    // pass for each item and call function render
    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {

        val item = itemGifList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount() = itemGifList.size

    fun getItemAt(position: Int): ItemGif? {
        if (position < 0 || position >= itemGifList.size) {
            null
        }
        return itemGifList[position]
    }
}