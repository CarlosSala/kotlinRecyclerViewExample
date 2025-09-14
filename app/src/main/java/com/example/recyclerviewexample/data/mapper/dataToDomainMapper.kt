package com.example.recyclerviewexample.data.mapper

import com.example.recyclerviewexample.data.model.GifDto
import com.example.recyclerviewexample.data.model.ItemGif
import com.example.recyclerviewexample.data.model.Meta
import com.example.recyclerviewexample.data.model.Original
import com.example.recyclerviewexample.data.model.UrlImage
import com.example.recyclerviewexample.domain.model.UrlImage as UrlImageDomain
import com.example.recyclerviewexample.domain.model.Original as OriginalDomain
import com.example.recyclerviewexample.domain.model.ItemGif as ItemGifDomain
import com.example.recyclerviewexample.domain.model.Meta as MetaDomain
import com.example.recyclerviewexample.domain.model.Gif as GifDomain


fun GifDto.toDomain(): GifDomain {
    return GifDomain(
        dataResponse = this.dataResponse.map { it.toDomain() }.toMutableList(),
        metaResponse = this.metaResponse.toDomain()
    )
}

fun Meta.toDomain(): MetaDomain {
    return MetaDomain(
        msg = this.msg,
        responseId = this.responseId,
        status = this.status
    )
}

fun ItemGif.toDomain(): ItemGifDomain {
    return ItemGifDomain(
        gifId = this.gifId,
        url = this.url,
        title = this.title,
        images = this.images.toDomain()
    )
}

fun Original.toDomain(): OriginalDomain {
    return OriginalDomain(original = this.original.toDomain())
}

fun UrlImage.toDomain(): UrlImageDomain {
    return UrlImageDomain(url = this.url)
}