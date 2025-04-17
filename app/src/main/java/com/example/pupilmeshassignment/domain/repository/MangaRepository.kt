package com.example.pupilmeshassignment.domain.repository

import com.example.pupilmeshassignment.data.local.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaList(page: Int, pageSize: Int): Flow<List<MangaEntity>>
}
