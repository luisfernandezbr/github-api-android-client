package br.com.luisfernandez.github.client.pojo

import com.google.gson.annotations.SerializedName
import java.util.Date

data class IssueResponse(
    @SerializedName("user") val user: User,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("created_at") val createdAt: Date
)