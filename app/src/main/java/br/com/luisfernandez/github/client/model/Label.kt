package br.com.luisfernandez.github.client.model

import com.google.gson.annotations.SerializedName
data class Label(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String,
    @SerializedName("default") val default: Boolean
)