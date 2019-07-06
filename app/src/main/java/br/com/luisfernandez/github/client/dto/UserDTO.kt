package br.com.luisfernandez.github.client.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)