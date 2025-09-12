package com.example.recyclerviewexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.model.ItemGif
import com.example.recyclerviewexample.data.network.GiftApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: GiftApiService
) : ViewModel() {

    private val _gifs = MutableStateFlow<List<ItemGif>>(listOf())
    val gifs: StateFlow<List<ItemGif>> get() = _gifs

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> get() = _error

    fun loadGifs(typeOfGif: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: GifDto = service.getGifs(typeOfGif, "25")
                if (response.metaResponse.status == 200) {
                    _gifs.value = (response.dataResponse)
                    _error.value = (null) // clean previews errors
                } else {
                    _error.value = ("Gifs couldn't be loaded: ${response.metaResponse.msg}")
                }
            } catch (e: Exception) {
                _error.value = ("Error de red: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        _gifs.update { currentList ->
            val newList = currentList.toMutableList()
            val item = newList.removeAt(fromPosition)
            newList.add(toPosition, item)
            newList
        }
    }

    fun deleteItemAt(position: Int) {
        _gifs.update { currentList ->
            val newList = currentList.toMutableList()
            newList.removeAt(position)
            newList
        }
    }

    fun deleteItem(itemToDelete: ItemGif) {
        _gifs.update { currentList ->
            val newList = currentList.toMutableList()
            newList.remove(itemToDelete)
            newList
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewmodelFactory(
    private val service: GiftApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}