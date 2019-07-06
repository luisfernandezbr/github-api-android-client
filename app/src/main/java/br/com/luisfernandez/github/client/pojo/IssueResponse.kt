package br.com.luisfernandez.github.client.pojo

import br.com.luisfernandez.github.client.dto.UserDTO
import com.google.gson.annotations.SerializedName
import java.util.Date

data class IssueResponse(
        @SerializedName("user") val userDTO: UserDTO,
        @SerializedName("title") val title: String,
        @SerializedName("body") val body: String,
        @SerializedName("created_at") val createdAt: Date
)