package com.example.pupilmeshassignment.data.remote


data class MangaResponse(
    val data: List<MangaDto>
)

data class MangaDto(
    val id: String,
    val title: String,
    val thumb: String,
    val summary: String
)


