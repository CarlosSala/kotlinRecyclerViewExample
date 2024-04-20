package com.example.recyclerviewexample

import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.data.ItemGif
import com.example.recyclerviewexample.databinding.ActivityMainBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Collections


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: GifAdapter

    /*    private val viewModel: MainViewModel by viewModels() {
         MainViewModel.MainViewModelFactory(emptyList())
     }*/
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.gifs.observe(this@MainActivity) {

            initRecyclerView(it)
            customRecyclerView(it)
        }
    }

    private fun initRecyclerView(gifs: MutableList<ItemGif>) {

        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)

        myAdapter = GifAdapter(gifs) { gif ->
            onItemSelected(
                gif
            )
        }

        binding.rvSuperhero.adapter = myAdapter
    }

    private fun customRecyclerView(itemGifList: MutableList<ItemGif>) {

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

        itemTouchHelper.attachToRecyclerView(binding.rvSuperhero)
    }

    private fun onItemSelected(superhero: ItemGif) {

        Toast.makeText(this, superhero.title, Toast.LENGTH_LONG).show()
    }
}