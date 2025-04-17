package com.example.pupilmeshassignment.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "manga",
    indices = [Index(value = ["id"])]   //optimizes query performance
)
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val imageUrl: String,
    val details: String
)
