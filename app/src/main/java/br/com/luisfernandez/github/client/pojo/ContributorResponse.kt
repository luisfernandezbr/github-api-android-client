package br.com.luisfernandez.github.client.pojo

import com.google.gson.annotations.SerializedName

data class ContributorResponse(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("contributions") val contributions: String
)