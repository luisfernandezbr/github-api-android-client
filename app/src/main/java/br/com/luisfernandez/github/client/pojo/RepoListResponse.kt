package br.com.luisfernandez.github.client.pojo

import br.com.luisfernandez.github.client.dto.RepoDTO
import com.google.gson.annotations.SerializedName

data class RepoListResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repoDTOList: List<RepoDTO>
)