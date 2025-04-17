package com.example.pupilmeshassignment.presentation.screens.manga

import androidx.lifecycle.ViewModel
import com.example.pupilmeshassignment.data.local.dao.MangaDao
import com.example.pupilmeshassignment.data.local.entity.MangaEntity

class MangaDetailsViewModel(private val dao: MangaDao) : ViewModel() {

    suspend fun getMangaById(id: String): MangaEntity? {
        return dao.getMangaById(id) // Fetch manga details from the database synchronously
    }
}
