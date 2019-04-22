package br.com.luisfernandez.github.client.pojo.commit

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Committer(
    @SerializedName("date") val date: Date
)