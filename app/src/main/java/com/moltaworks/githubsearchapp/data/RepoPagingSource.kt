package com.moltaworks.githubsearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moltaworks.githubsearchapp.api.Request
import com.moltaworks.githubsearchapp.api.GitHubApi
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class RepoPagingSource (private val api: GitHubApi, val query: String) : PagingSource<Int, GitHubRepos>() {

    override fun getRefreshKey(state: PagingState<Int, GitHubRepos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubRepos> {
        return try {
            // Start refresh at page 1 if undefined.
            val page = params.key ?: 1
            // Suspending network load via Retrofit. This doesn't need to be wrapped in a
            // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
            // CallAdapter dispatches on a worker thread.
            val response = api.searchGitHub(query, 30, page)
            LoadResult.Page(
                data = extractData(response),
                prevKey = null,
                nextKey = if (extractData(response).isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }

    }

    private fun extractData(response: Response<Request>): List<GitHubRepos> {
        if (response.isSuccessful) {
            return response.body()?.gitHubRepos ?: emptyList()
        }else{
            Timber.e(response.message())
        }
        return emptyList()
    }
}