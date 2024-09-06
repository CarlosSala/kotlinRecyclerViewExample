package com.example.recyclerviewexample

import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.data.ItemGif
import com.example.recyclerviewexample.data.RetrofitService
import com.example.recyclerviewexample.databinding.ActivityMainBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Collections


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: GifAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitService.RetrofitServiceFactory.makeRetrofitService()

        val viewModelFactory = MainViewmodelFactory(retrofitService)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.loadGifs("laugh")

        viewModel.gifs.observe(this@MainActivity) { gifs ->

            initRecyclerView(gifs)
            customRecyclerView(gifs)
        }
    }

    private fun initRecyclerView(gifs: List<ItemGif>) {

        binding.rvGif.layoutManager = LinearLayoutManager(this)

        myAdapter = GifAdapter(gifs) { gif ->
            onItemSelected(
                gif
            )
        }

        binding.rvGif.adapter = myAdapter
    }

    private fun customRecyclerView(itemGifList: List<ItemGif>) {

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = viewHolder.bindingAdapterPosition
                val targetPosition = target.bindingAdapterPosition

                Collections.swap(itemGifList, sourcePosition, targetPosition)
                myAdapter.notifyItemMoved(sourcePosition, targetPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.deleteItem(viewHolder.bindingAdapterPosition)
            }

            // external dependency
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity, R.color.my_red
                    )
                )

                    .addActionIcon(R.drawable.baseline_delete_24)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.rvGif)
    }

    private fun onItemSelected(gif: ItemGif) {
        Toast.makeText(this, gif.title, Toast.LENGTH_LONG).show()
    }
}