package com.example.recyclerviewexample.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewexample.ui.model.ItemGif

class GifDiffUtil(
    private val oldList: List<ItemGif>,
    private val newList: List<ItemGif>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].gifId == newList[newItemPosition].gifId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}