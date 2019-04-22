package br.com.luisfernandez.github.client.pojo

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CommitResponse(
    @SerializedName("user") val author: User,
    @SerializedName("message") val message: String,
    @SerializedName("date") val date: Date
)