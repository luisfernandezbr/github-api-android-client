package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.pojo.RepoListResponse

interface RepoListRepository {
    suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<RepoListResponse, String>
}