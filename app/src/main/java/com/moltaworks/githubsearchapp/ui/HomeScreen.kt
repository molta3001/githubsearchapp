package com.moltaworks.githubsearchapp.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.moltaworks.githubsearchapp.MainViewModel
import kotlinx.coroutines.flow.emptyFlow

@Preview(showSystemUi = true)
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {
    val textState = rememberSaveable { mutableStateOf("") }
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