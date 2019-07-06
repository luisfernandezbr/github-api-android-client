package br.com.luisfernandez.github.client.pojo

import br.com.luisfernandez.github.client.dto.UserDTO
import com.google.gson.annotations.SerializedName
import java.util.Date

data class PullRequestResponse(
        @SerializedName("html_url") val htmlUrl: String,
        @SerializedName("title") val title: String,
        @SerializedName("user") val userDTO: UserDTO,
        @SerializedName("body") val body: String,
        @SerializedName("created_at") val createdAt: Date
)