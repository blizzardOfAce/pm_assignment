package com.example.pupilmeshassignment.domain.useCase.manga

import com.example.pupilmeshassignment.data.local.entity.MangaEntity
import com.example.pupilmeshassignment.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow


class GetMangaListUseCase(private val repository: MangaRepository) {
    operator fun invoke(page: Int, pageSize: Int): Flow<List<MangaEntity>> {
        return repository.getMangaList(page, pageSize)
    }
}


