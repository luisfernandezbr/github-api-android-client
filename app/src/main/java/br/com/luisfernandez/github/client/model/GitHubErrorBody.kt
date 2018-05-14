package br.com.luisfernandez.github.client.model

import com.google.gson.annotations.SerializedName

data class GitHubErrorBody(
    @SerializedName("message") val message: String,
    @SerializedName("documentation_url") val documentationUrl: String
)