package br.com.luisfernandez.github.client.mvvm.repository.pojo

import com.google.gson.annotations.SerializedName
import java.util.*

data class PullRequestResponse(
        @SerializedName("html_url") val htmlUrl: String,
        @SerializedName("title") val title: String,
        @SerializedName("user") val user: User,
        @SerializedName("body") val body: String,
        @SerializedName("created_at") val createdAt: Date
)