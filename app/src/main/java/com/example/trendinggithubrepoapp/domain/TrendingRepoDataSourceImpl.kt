package com.example.trendinggithubrepoapp.domain

import com.example.trendinggithubrepoapp.data.TrendingRepoDataSource
import com.example.trendinggithubrepoapp.data.network.TrendingAPI
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingRepoDataSourceImpl @Inject constructor(
   private val trendingAPI: TrendingAPI
): TrendingRepoDataSource {
    override fun getTrendingRepo(): Flow<List<RepoData>> {
        return flow {
            val response = trendingAPI.getTrendingRepos()
            emit(response)
        }
    }
}