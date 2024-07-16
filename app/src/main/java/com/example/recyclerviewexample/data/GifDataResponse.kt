package com.example.recyclerviewexample.data

import com.google.gson.annotations.SerializedName


data class GifDataResponse(

    @SerializedName("data") val dataResponse: MutableList<ItemGif>,
    @SerializedName("meta") val metaResponse: Meta
)

data class Meta(
    val msg: String,
    val responseId: String,
    val status: Int
)

data class ItemGif(
    @SerializedName("id") val gifId: String,
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: Original
)

data class Original(
    @SerializedName("original") val original: UrlImage
)

data class UrlImage(
    @SerializedName("url") val url: String
)
