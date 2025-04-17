package com.example.pupilmeshassignment.data.repository

import android.util.Log
import com.example.pupilmeshassignment.data.local.dao.MangaDao
import com.example.pupilmeshassignment.data.local.entity.MangaEntity
import com.example.pupilmeshassignment.data.remote.MangaApi
import com.example.pupilmeshassignment.domain.repository.MangaRepository
import com.example.pupilmeshassignment.utils.NetworkChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MangaRepositoryImpl(
    private val api: MangaApi,
    private val dao: MangaDao,
    private val networkChecker: NetworkChecker
) : MangaRepository {

    override fun getMangaList(page: Int, pageSize: Int): Flow<List<MangaEntity>> = flow {
        val offset = (page - 1) * pageSize
        val local = dao.getMangaList(pageSize, offset)
        val isOnline = networkChecker.isOnline()
        emit(local)

        if (local.isEmpty() && isOnline) {

            try {
                val response = withContext(Dispatchers.IO) {
                    api.fetchManga(page)
                }

                if (response.data.isNotEmpty()) {
                    val entities = response.data.map {
                        MangaEntity(
                            id = it.id,
                            title = it.title,
                            imageUrl = it.thumb,
                            details = it.summary
                        )
                    }
                    dao.insertMangaList(entities)
                    emit(dao.getMangaList(pageSize, offset))
                }
            } catch (e: Exception) {
                Log.e("MangaRepository", "Fetch error", e)
            }
        }
    }.flowOn(Dispatchers.IO)

}


