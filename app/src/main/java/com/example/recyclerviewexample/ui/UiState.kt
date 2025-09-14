package com.example.recyclerviewexample.ui

import com.example.recyclerviewexample.ui.model.ItemGif

sealed class UiState {
    object Loading : UiState()
    data class Success(val gifList: List<ItemGif>) : UiState()
    data class Error(val error: String) : UiState()
}