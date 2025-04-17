package com.example.pupilmeshassignment.presentation.screens.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pupilmeshassignment.R
import com.example.pupilmeshassignment.presentation.components.MangaCard
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    viewModel: MangaViewModel = koinViewModel(),
    onClickManga: (String) -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        if (state.mangaList.isEmpty()) {
            viewModel.loadManga()
        }
    }

    // Trigger data load when last visible item is near the end
    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = state.mangaList.size
            lastVisibleItemIndex to totalItemsCount
        }
            .distinctUntilChanged()
            .collect { (lastVisibleIndex, totalItems) ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= totalItems - 5 &&
                    !state.isLoading &&
                    !state.isEndReached
                ) {
                    viewModel.loadManga()
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Manga") },
                    actions = {
                        IconButton(modifier = Modifier.padding(horizontal = 8.dp).size(28.dp), onClick = {
                            viewModel.logout()
                            onLogout()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.logout_32dp),
                                contentDescription = "Logout"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = listState,
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    start = 4.dp,
                    end = 4.dp,
                    bottom = innerPadding.calculateBottomPadding() + 70.dp // Extra space for loading indicator
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.mangaList) { manga ->
                    MangaCard(manga, onClick = { onClickManga(manga.id) })
                }
            }
        }

        // Initial loading state (full screen)
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}
