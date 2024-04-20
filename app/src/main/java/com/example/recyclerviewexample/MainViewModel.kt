package com.example.recyclerviewexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.data.GifDataResponse
import com.example.recyclerviewexample.data.ItemGif
import com.example.recyclerviewexample.data.RetrofitService
import kotlinx.coroutines.launch

// the app logic goes here, and ui only print the info in the screen
// architecture component: for example ViewModel
// if we rotate the screen, the viewModel stays stable

// class MainViewModel(anyData: List<Any>) : ViewModel() {
class MainViewModel() : ViewModel() {

    private val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()


    // it the viewModel receive args, this need a factory to generate a viewModel
    /*    class MainViewModelFactory(private val anyData: List<Any>) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T{
                return MainViewModel(anyData) as T
            }
        }*/
    /*
        private val _loading = MutableLiveData(false)
        val loading: LiveData<ItemGif> get() = _loading*/

    private val _gifs = MutableLiveData<MutableList<ItemGif>>()
    val gifs: MutableLiveData<MutableList<ItemGif>> get() = _gifs

    init {
        viewModelScope.launch {

            val myResponse: GifDataResponse = service.getGifs("laugh", "25")

            if (myResponse.metaResponse.status == 200) {

                _gifs.value = myResponse.dataResponse

            }
        }
    }

}