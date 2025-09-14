package com.example.recyclerviewexample.domain.model

data class Gif(
    val dataResponse: MutableList<ItemGif>,
    val metaResponse: Meta
)

data class Meta(
    val msg: String,
    val responseId: String?,
    val status: Int
)

data class ItemGif(
    val gifId: String,
    val url: String,
    val title: String,
    val images: Original
)

data class Original(
    val original: UrlImage
)

data class UrlImage(
    val url: String
)
