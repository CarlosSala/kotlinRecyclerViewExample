package com.example.recyclerviewexample.ui.model

import com.example.recyclerviewexample.data.model.ItemGif

data class UiState(
    val isLoading: Boolean = false,
    val gifList: List<ItemGif> = emptyList(),
    val error: String? = null,
    val success: String? = null
)
