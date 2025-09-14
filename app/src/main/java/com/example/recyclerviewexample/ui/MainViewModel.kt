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
import com.example.recyclerviewexample.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGifsUseCase: GetGifsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadGifs(typeOfGif: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                val response: GifDto = getGifsUseCase(typeOfGif = typeOfGif, limit = "25")
                if (response.metaResponse.status == 200) {
                    UiState.Success(gifList = response.dataResponse)
                } else {
                    UiState.Error(error = response.metaResponse.msg)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(error = e.message ?: "Unknown error")
            } as UiState
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                val currentList = currentState.gifList.toMutableList()
                val item = currentList.removeAt(fromPosition)
                currentList.add(toPosition, item)
                currentState.copy(gifList = currentList)
            } else {
                currentState
            }
        }
    }

    fun deleteItem(itemToDelete: ItemGif) {
        _uiState.update { currentUiState ->
            if (currentUiState is UiState.Success) {
                val newList = currentUiState.gifList.toMutableList()
                newList.remove(itemToDelete)
                currentUiState.copy(gifList = newList)
            } else {
                currentUiState
            }
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