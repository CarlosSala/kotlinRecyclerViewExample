package com.example.recyclerviewexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.data.ItemGif

class GifAdapter(
    private val itemGifList: List<ItemGif>,
    private val onClickListener: (ItemGif) -> Unit
) :
    RecyclerView.Adapter<GifViewHolder>() {

    private val mutableItemGif: MutableList<ItemGif> = itemGifList.toMutableList()

    // return a item to ViewHolder by each object of superhero
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return GifViewHolder(layoutInflater.inflate(R.layout.item_gif, parent, false))
    }

    // pass for each item and call function render
    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {

        val item = mutableItemGif[position]
        holder.render(item, onClickListener)
    }

    // is the number of item that rv will show in the screen
    override fun getItemCount(): Int = mutableItemGif.size

    fun deleteItem(adapterPosition: Int) {

        mutableItemGif.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

}