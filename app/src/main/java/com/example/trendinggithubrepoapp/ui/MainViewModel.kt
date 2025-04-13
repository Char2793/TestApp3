package com.example.trendinggithubrepoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import com.example.trendinggithubrepoapp.data.repository.TrendingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trendingRepo: TrendingRepo
): ViewModel() {

    private val _repoList: MutableStateFlow<List<RepoData>> = MutableStateFlow(listOf())
    val repoList = _repoList.asStateFlow()

    fun getRepoList() {
        viewModelScope.launch {
            trendingRepo.getTrendingRepo().collect {
                _repoList.value = it
            }
        }
    }

}