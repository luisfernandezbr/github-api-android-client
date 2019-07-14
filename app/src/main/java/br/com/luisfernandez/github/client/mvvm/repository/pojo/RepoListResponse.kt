package br.com.luisfernandez.github.client.mvvm.repository.pojo

import com.google.gson.annotations.SerializedName

data class RepoListResponse(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        @SerializedName("items") val repos: List<Repo>
)