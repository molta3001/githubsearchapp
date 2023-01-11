package com.moltaworks.githubsearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moltaworks.githubsearchapp.api.GitHubApi
import kotlinx.coroutines.flow.Flow

class Repository(private val api: GitHubApi) {

    fun searchGitHubRepository(query: String):  Flow<PagingData<GitHubRepos>> {
         val pager = Pager(
                config = PagingConfig(
                    pageSize = 30,
                ),
                pagingSourceFactory = {
                    RepoPagingSource(api, query)
                }
            ).flow
        return pager

    }
}