package com.moltaworks.githubsearchapp.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.moltaworks.githubsearchapp.MainViewModel
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {

    val textState = remember { mutableStateOf("") }
    val viewModel = hiltViewModel<MainViewModel>()

    fun onClear() {
        viewModel.flow = emptyFlow()
    }

    Scaffold(
        topBar = {
            SearchBar(state = textState, onClearClick = ::onClear)
        },
        content = { padding ->
            Column() {
                if (textState.value.isNotBlank()) {
                    PagingListScreen(repos = viewModel.getResponse(textState.value), padding)
                }
            }

        },
    )
}


