package br.com.luisfernandez.github.client.pojo

import com.google.gson.annotations.SerializedName

data class ContributorResponse(
    @SerializedName("login") val user: String,
    @SerializedName("avatar_url") val title: String,
    @SerializedName("contributions") val body: Int
)