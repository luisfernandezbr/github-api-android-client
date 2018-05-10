package br.com.luisfernandez.github.client.model

import com.google.gson.annotations.SerializedName
data class License(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("spdx_id") val spdxId: String,
    @SerializedName("url") val url: String
)