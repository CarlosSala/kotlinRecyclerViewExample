package com.example.recyclerviewexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.data.model.ItemGif

class GifAdapter(
    private var itemGifList: List<ItemGif>,
    private val onClickListener: (ItemGif) -> Unit
) :
    RecyclerView.Adapter<GifViewHolder>() {

    fun updateList(newList: List<ItemGif>) {
        val gifDiffUtil = GifDiffUtil(itemGifList, newList)
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


    // is the number of item that rv will show in the screen
    // override fun getItemCount(): Int = mutableItemGif.size

    /* fun deleteItem(adapterPosition: Int) {

         mutableItemGif.removeAt(adapterPosition)
         notifyItemRemoved(adapterPosition)
     }*/

    /* fun getItemAt(position: Int): ItemGif? {
         return if (position >= 0 && position < currentList.size) {
             getItem(position)
         } else {
             null
         }

     }*/
}