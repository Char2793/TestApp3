package com.example.trendinggithubrepoapp.data.repository

import com.example.trendinggithubrepoapp.data.network.models.RepoData
import kotlinx.coroutines.flow.Flow

interface TrendingRepo {

    fun getTrendingRepo(): Flow<List<RepoData>>

}