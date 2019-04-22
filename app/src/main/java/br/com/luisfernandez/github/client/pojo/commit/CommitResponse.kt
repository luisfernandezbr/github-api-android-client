package br.com.luisfernandez.github.client.pojo.commit

import br.com.luisfernandez.github.client.pojo.User
import com.google.gson.annotations.SerializedName

data class CommitResponse(
        @SerializedName("author") val author: User,
        @SerializedName("commit") val commit: Commit
)