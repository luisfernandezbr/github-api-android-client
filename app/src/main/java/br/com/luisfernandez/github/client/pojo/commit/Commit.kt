package br.com.luisfernandez.github.client.pojo.commit

import com.google.gson.annotations.SerializedName

data class Commit(
        @SerializedName("committer") val committer: Committer,
        @SerializedName("message") val message: String
)