package com.moltaworks.githubsearchapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.moltaworks.githubsearchapp.data.GitHubRepos
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
fun PagingListScreen(repos: Flow<PagingData<GitHubRepos>>, padding: PaddingValues?) {

    val lazyPagingItems = repos.collectAsLazyPagingItems()

    LazyColumn {
        items(
            items = lazyPagingItems,
            key = { it.html_url }
        ) { ghRepo ->
            ghRepo?.let {
                ProfileCardComposable(it)
            }
            Divider()
        }
        when (val state = lazyPagingItems.loadState.refresh) { // FIRST LOAD
            is LoadState.Error -> {
                item {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Ups! Something went wrong!"
                    )
                }
                Timber.e(state.error)
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {
            }
        }

        when (val state = lazyPagingItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                item {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Ups! Something went wrong!"
                    )
                }
                Timber.e(state.error)
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileCardComposable(ghRepo: GitHubRepos) {

    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.Yellow)
            .fillMaxWidth()
    ) {

        ghRepo.owner.let {
            Column(
                modifier = Modifier
                    .padding(2.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    text = it.login,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                )
                Card(
                    shape = CircleShape,
                    border = BorderStroke(2.dp, color = Color.Green),
                    modifier = Modifier
                        .size(120.dp),
                    elevation = 4.dp
                ) {
                    GlideImage(
                        model = it.avatar_url,
                        contentDescription = it.login,
                        modifier = Modifier.size(260.dp)
                    )
                }
            }
        }
        ghRepo.let {
            Column() {
                Text(
                    modifier = Modifier
                        .padding(1.dp),
                    text = it.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(1.dp),
                    text = it.description ?: "",
                    maxLines = 3
                )
                HTMLurl(it.html_url)
            }
        }

    }
}

@Composable
fun HTMLurl(url: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }

    Text(
        modifier = Modifier
            .padding(1.dp)
            .clickable {
                context.startActivity(intent)
            },
        text = url,
        color = Color.Blue
    )
}