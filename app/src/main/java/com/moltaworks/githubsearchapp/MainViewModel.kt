package com.moltaworks.githubsearchapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moltaworks.githubsearchapp.data.GitHubRepos
import com.moltaworks.githubsearchapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var flow: Flow<PagingData<GitHubRepos>> = emptyFlow()

    fun getResponse(query: String): Flow<PagingData<GitHubRepos>> {
        if (query.isNotBlank()) {
            flow = repository.searchGitHubRepository(query).cachedIn(viewModelScope)
        }
        return flow
    }
}