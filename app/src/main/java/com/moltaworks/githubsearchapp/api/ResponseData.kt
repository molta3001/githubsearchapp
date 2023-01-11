package com.moltaworks.githubsearchapp.api

import com.google.gson.annotations.SerializedName
import com.moltaworks.githubsearchapp.data.GitHubRepos

data class Request(
    val total_count: Int,
    val incomplete_results: String,
    @SerializedName("items") val gitHubRepos: List<GitHubRepos>
)