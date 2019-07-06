package br.com.luisfernandez.github.client.dto

import com.google.gson.annotations.SerializedName

data class RepoDTO(
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("owner") val owner: UserDTO,
        @SerializedName("full_name") val fullName: String,
        @SerializedName("stargazers_count") val stargazersCount: Int,
        @SerializedName("watchers_count") val watchersCount: Int,
        @SerializedName("forks_count") val forksCount: Int,
        @SerializedName("open_issues_count") val openIssuesCount: Int
)