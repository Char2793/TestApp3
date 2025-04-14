package com.example.trendinggithubrepoapp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import com.example.trendinggithubrepoapp.data.repository.TrendingRepo
import com.example.trendinggithubrepoapp.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val coroutineRule =  MainCoroutineRule()

    @Mock
    lateinit var trendingRepo: TrendingRepo

    lateinit var mainViewModel: MainViewModel


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(trendingRepo)

    }

    @Test
    fun `test getRepos handles success response`()  = runTest{
        //Arrange

        val mockData = listOf(
            RepoData("Repo 1", "imageUrl", 300),
            RepoData("Repo 2", "imageUrl", 200),

            )

      val flow = flowOf(mockData)
        Mockito.`when`(trendingRepo.getTrendingRepo()).thenReturn(flow)

        //Act
        mainViewModel = MainViewModel(trendingRepo)

        val state = mainViewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(mockData, state.repoData)
        assertNull(state.error)



    }

    @Test
    fun `test getRepos emits error when exception is thrown`() = runTest {
        //Arrange

        val exception = RuntimeException("Exception is thrown")

        val flow = flow<List<RepoData>> {
            throw exception
        }
        Mockito.`when`(trendingRepo.getTrendingRepo()).thenReturn(flow)

        //Act
        mainViewModel = MainViewModel(trendingRepo)

        val state = mainViewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals("failed to fetch data", state.error)



    }
}