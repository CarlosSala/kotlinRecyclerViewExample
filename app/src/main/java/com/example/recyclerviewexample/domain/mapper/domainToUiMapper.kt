package com.example.recyclerviewexample.domain.mapper

import com.example.recyclerviewexample.domain.model.UrlImage
import com.example.recyclerviewexample.domain.model.Original
import com.example.recyclerviewexample.domain.model.ItemGif
import com.example.recyclerviewexample.domain.model.Meta
import com.example.recyclerviewexample.domain.model.Gif
import com.example.recyclerviewexample.ui.model.UrlImage as UrlImageUi
import com.example.recyclerviewexample.ui.model.Original as OriginalUi
import com.example.recyclerviewexample.ui.model.ItemGif as ItemGifUi
import com.example.recyclerviewexample.ui.model.Meta as MetaUi
import com.example.recyclerviewexample.ui.model.GifUi

fun Gif.toUi(): GifUi {
    return GifUi(
        dataResponse = dataResponse.map { it.toUi() }.toMutableList(),
        metaResponse = this.metaResponse.toUi()
    )
}

fun Meta.toUi(): MetaUi {
    return MetaUi(
        msg = this.msg,
        responseId = this.responseId,
        status = this.status
    )
}

fun ItemGif.toUi(): ItemGifUi {
    return ItemGifUi(
        gifId = this.gifId,
        url = this.url,
        title = this.title,
        images = this.images.toUi()
    )
}

fun Original.toUi(): OriginalUi {
    return OriginalUi(original = this.original.toUi())
}

fun UrlImage.toUi(): UrlImageUi {
    return UrlImageUi(url = this.url)
}