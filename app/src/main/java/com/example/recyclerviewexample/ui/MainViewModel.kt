package com.example.recyclerviewexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.data.datasource.remote.GifsRemoteDataSourceImpl
import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.model.ItemGif
import com.example.recyclerviewexample.data.network.RetrofitHelper
import com.example.recyclerviewexample.data.repository.GifsRepositoryImpl
import com.example.recyclerviewexample.domain.usecases.GetGifsUseCase
import com.example.recyclerviewexample.ui.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGifsUseCase: GetGifsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadGifs(typeOfGif: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val response: GifDto = getGifsUseCase(typeOfGif = typeOfGif, limit = "25")
                if (response.metaResponse.status == 200) {
                    _uiState.update { it.copy(gifList = response.dataResponse) }
                } else {
                    _uiState.update { it.copy(error = response.metaResponse.msg) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(success = "Success") }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        _uiState.update { uiState ->
            val newList = uiState.gifList.toMutableList()
            val item = newList.removeAt(fromPosition)
            newList.add(toPosition, item)
            uiState.copy(gifList = newList)
        }
    }

    /*   fun deleteItemAt(position: Int) {
           _gifs.update { currentList ->
               val newList = currentList.toMutableList()
               newList.removeAt(position)
               newList
           }
       }*/

    fun deleteItem(itemToDelete: ItemGif) {
        _uiState.update { uiState ->
            val newList = uiState.gifList.toMutableList()
            newList.remove(itemToDelete)
            uiState.copy(gifList = newList)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewmodelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val giftApiService = RetrofitHelper.getInstanceRetrofit()
            val gifsRemoteDataSourceImpl = GifsRemoteDataSourceImpl(giftApiService)
            val gifsRepositoryImpl = GifsRepositoryImpl(gifsRemoteDataSourceImpl)
            val getGifsUseCase = GetGifsUseCase(gifsRepositoryImpl)
            return MainViewModel(getGifsUseCase = getGifsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}