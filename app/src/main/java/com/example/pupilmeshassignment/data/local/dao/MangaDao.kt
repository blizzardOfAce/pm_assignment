package com.example.pupilmeshassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pupilmeshassignment.data.local.entity.MangaEntity

@Dao
interface MangaDao {

    @Query("SELECT * FROM manga ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getMangaList(limit: Int, offset: Int): List<MangaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaList(manga: List<MangaEntity>)


    @Query("SELECT * FROM manga WHERE id = :id")
    suspend fun getMangaById(id: String): MangaEntity?
}

