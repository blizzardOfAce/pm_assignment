package com.example.pupilmeshassignment.presentation.screens.manga

import com.example.pupilmeshassignment.data.local.entity.MangaEntity

data class MangaState(
    val mangaList: List<MangaEntity> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 1,
    val isEndReached: Boolean = false
)

