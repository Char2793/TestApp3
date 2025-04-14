package com.example.trendinggithubrepoapp.utils

import com.example.trendinggithubrepoapp.data.network.models.RepoData

data class TrendingRepoUIState (

    val isLoading: Boolean = false,
    val repoData: List<RepoData> = emptyList(),
    val error: String? = null
)