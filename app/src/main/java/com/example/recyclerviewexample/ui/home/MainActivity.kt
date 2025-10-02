package com.example.recyclerviewexample.ui.home

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.databinding.ActivityMainBinding
import com.example.recyclerviewexample.ui.adapter.GifAdapter
import com.example.recyclerviewexample.ui.model.ItemGif
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gifAdapter: GifAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gifAdapter = GifAdapter(emptyList()) { gif ->
            onItemSelected(gif)
        }

        binding.rvGif.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gifAdapter
        }

        setupItemTouchHelper()

        viewModel.loadGifs("laugh")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    when (uiState) {
                        is UiState.Error -> errorState(uiState)
                        is UiState.Loading -> loadingState()
                        is UiState.Success -> successState(uiState)
                    }
                }
            }

            if (savedInstanceState == null) {
                viewModel.loadGifs("laugh")
            }
        }
    }


    fun loadingState() {
        binding.pbLoading.isVisible = true
    }

    fun errorState(uiState: UiState.Error) {
        binding.pbLoading.isVisible = false
        Log.e("MainActivity", "Error State: ${uiState.error}")
        Toast.makeText(
            this@MainActivity,
            "Error: ${uiState.error}",
            Toast.LENGTH_LONG
        ).show()
    }

    fun successState(uiState: UiState.Success) {
        binding.pbLoading.isVisible = false
        gifAdapter.updateList(uiState.gifList)
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
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val sourcePosition = viewHolder.bindingAdapterPosition
                val targetPosition = target.bindingAdapterPosition

                if (sourcePosition == RecyclerView.NO_POSITION || targetPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                viewModel.moveItem(sourcePosition, targetPosition)
                // gifAdapter.notifyItemMoved(sourcePosition, targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    val itemToDelete = gifAdapter.getItemAt(position)

                    if (itemToDelete != null) {
                        viewModel.deleteItem(itemToDelete)
                    }
                    // gifAdapter.notifyItemRemoved(position)
                }
            }

            // external dependency
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
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