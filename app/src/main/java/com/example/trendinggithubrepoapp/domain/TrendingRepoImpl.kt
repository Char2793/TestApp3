package com.example.trendinggithubrepoapp.domain

import com.example.trendinggithubrepoapp.data.TrendingRepoDataSource
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import com.example.trendinggithubrepoapp.data.repository.TrendingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepoImpl @Inject constructor(
    private val trendingRepoDataSource: TrendingRepoDataSource
): TrendingRepo {
    override fun getTrendingRepo(): Flow<List<RepoData>> {
        return trendingRepoDataSource.getTrendingRepo()
    }
}