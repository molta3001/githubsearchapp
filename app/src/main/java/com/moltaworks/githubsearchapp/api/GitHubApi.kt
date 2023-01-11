package com.moltaworks.githubsearchapp.api

import ir.logicbase.mockfit.Mock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @Mock("gitHub_search.json")
    @GET("repositories")
    suspend fun searchGitHub(
        @Query("q") query: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ) : Response<Request>
}