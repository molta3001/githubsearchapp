package com.moltaworks.githubsearchapp.data

data class GitHubRepos(
    val name: String, // repo Name
    val description: String,
    val html_url: String,
    val owner: Owner
)

data class Owner(
    val avatar_url: String,
    val login: String, // owner name
)