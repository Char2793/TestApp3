package com.example.trendinggithubrepoapp.data.network.models

import com.google.gson.annotations.SerializedName

data class RepoData (

    @SerializedName("name")
    val repoName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("stargazers_count")
    val starCount: Int
)
