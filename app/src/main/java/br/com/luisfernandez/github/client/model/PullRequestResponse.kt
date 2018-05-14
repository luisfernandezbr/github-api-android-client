package br.com.luisfernandez.github.client.model

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: User,
    @SerializedName("body") val body: String,
    @SerializedName("created_at") val createdAt: String
)