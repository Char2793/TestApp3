package com.example.trendinggithubrepoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.trendinggithubrepoapp.data.network.models.RepoData
import com.example.trendinggithubrepoapp.ui.theme.TrendingGithubRepoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingGithubRepoAppTheme {
                Scaffold(
                    topBar = { TopAppBar() }
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {

    Surface(modifier = modifier) {
        val viewModel: MainViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        when {
            uiState.isLoading -> LoadingScreen()
            uiState.repoData.isNotEmpty() -> TrendingRepoScreen(uiState.repoData)
            uiState.error != null -> ErrorScreen(uiState.error.toString())
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val viewModel: MainViewModel = hiltViewModel()
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text("Trending")
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = ""
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text (text = "Sort By Stars") },
                    onClick = { viewModel.sortRepoList("Star Count") }
                )

                DropdownMenuItem(
                    text = { Text (text = "Sort By Name") },
                    onClick = { viewModel.sortRepoList("Name") }
                )
            }

        }
    )
}

@Composable
fun LoadingScreen() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message", color = Color.Red)
    }
}

@Composable
fun TrendingRepoScreen(trendingRepos: List<RepoData>) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            LazyColumn {
                items(trendingRepos) { item ->
                    TrendingRepoItem(item)
                }
            }
        }
    }

}

@Composable
fun TrendingRepoItem(repoItem: RepoData) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        Row() {
            AsyncImage(
                model = repoItem.avatarUrl,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.Gray, shape = CircleShape)
            )
            Column  {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "ocktokit"
                )
                Text(
                    text = repoItem.repoName
                )
            }

        }

    }


}
