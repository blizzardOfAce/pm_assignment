package com.example.pupilmeshassignment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pupilmeshassignment.data.local.dao.MangaDao
import com.example.pupilmeshassignment.data.local.dao.UserDao
import com.example.pupilmeshassignment.data.local.entity.MangaEntity
import com.example.pupilmeshassignment.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, MangaEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
    abstract fun userDao(): UserDao
}

