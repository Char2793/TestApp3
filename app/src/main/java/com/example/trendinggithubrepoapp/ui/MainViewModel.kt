package com.example.trendinggithubrepoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import com.example.trendinggithubrepoapp.data.repository.TrendingRepo
import com.example.trendinggithubrepoapp.utils.TrendingRepoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trendingRepo: TrendingRepo
): ViewModel() {

    private val _uiState = MutableStateFlow(TrendingRepoUIState())
    val uiState: StateFlow<TrendingRepoUIState> = _uiState

    var repoList: List<RepoData> = emptyList()

    init {
        getRepoList()
    }

    private fun getRepoList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                trendingRepo.getTrendingRepo().collect {
                    repoList = it
                }
                _uiState.update { it.copy(isLoading = false, repoData = repoList) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }

        }
    }

    fun sortRepoList(query: String) {
        var sortedList: List<RepoData> = listOf()

        sortedList = if (query == "Name") {
            repoList.sortedBy { repoData ->
                repoData.repoName
            }
        } else {
            repoList.sortedBy { repoData ->
                repoData.starCount
            }
        }

        _uiState.update { it.copy(repoData = sortedList) }
    }
}