package com.example.trendinggithubrepoapp.data.network

import com.example.trendinggithubrepoapp.data.network.models.RepoData
import retrofit2.http.GET

interface TrendingAPI {

    @GET("repos")
    suspend fun getTrendingRepos(): List<RepoData>
}