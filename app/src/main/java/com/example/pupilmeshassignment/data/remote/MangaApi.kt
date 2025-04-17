package com.example.pupilmeshassignment.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApi {
    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int
    ): MangaResponse
}

