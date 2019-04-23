package br.com.luisfernandez.github.client.pojo

import com.google.gson.annotations.SerializedName

data class BranchResponse(
    @SerializedName("name") val name: String
)