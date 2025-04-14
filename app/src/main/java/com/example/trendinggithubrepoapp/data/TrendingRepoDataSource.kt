package com.example.trendinggithubrepoapp.data

import com.example.trendinggithubrepoapp.data.network.models.RepoData
import kotlinx.coroutines.flow.Flow

interface TrendingRepoDataSource {

    fun getTrendingRepo(): Flow<List<RepoData>>

}