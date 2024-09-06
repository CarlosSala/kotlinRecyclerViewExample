package com.example.recyclerviewexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.data.GifDataResponse
import com.example.recyclerviewexample.data.ItemGif
import com.example.recyclerviewexample.data.RetrofitService
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: RetrofitService
) : ViewModel() {

    private val _gifs = MutableLiveData<List<ItemGif>>()
    val gifs: LiveData<List<ItemGif>> get() = _gifs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun loadGifs(typeOfGif: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: GifDataResponse = service.getGifs(typeOfGif, "25")
                if (response.metaResponse.status == 200) {
                    _gifs.postValue(response.dataResponse)
                    _error.postValue(null) // clean previews errors
                } else {
                    _error.postValue("Gifs couldn't be loaded: ${response.metaResponse.msg}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewmodelFactory(
    private val service: RetrofitService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}