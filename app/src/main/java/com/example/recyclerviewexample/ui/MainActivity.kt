package com.example.recyclerviewexample.ui

import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.ui.adapter.GifAdapter
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.data.model.ItemGif
import com.example.recyclerviewexample.data.network.RetrofitHelper
import com.example.recyclerviewexample.databinding.ActivityMainBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gifAdapter: GifAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val giftApiService = RetrofitHelper.getInstanceRetrofit()

        val viewModelFactory = MainViewmodelFactory(giftApiService)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.loadGifs("laugh")

        setupItemTouchHelper()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gifs.collect { gifs ->
                    setupRecyclerView(gifs)
                }
            }

            if (savedInstanceState == null) {
                viewModel.loadGifs("laugh")
            }
        }
    }

    private fun setupRecyclerView(gifs: List<ItemGif>) {

        gifAdapter = GifAdapter(gifs) { gif ->
            onItemSelected(gif)
        }

        binding.rvGif.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gifAdapter
        }
    }

    private fun setupItemTouchHelper() {

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


                if (sourcePosition == RecyclerView.NO_POSITION || targetPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                // Collections.swap(itemGifList, sourcePosition, targetPosition)
                // viewModel.moveItem(sourcePosition, targetPosition)
                gifAdapter.notifyItemMoved(sourcePosition, targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    //  viewModel.deleteItemAt(position)
                    gifAdapter.notifyItemRemoved(position)
                }
                // myAdapter.deleteItem(viewHolder.bindingAdapterPosition)
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