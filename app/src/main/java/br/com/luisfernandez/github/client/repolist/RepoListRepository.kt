package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.Repo

interface RepoListRepository {
    suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody>
}