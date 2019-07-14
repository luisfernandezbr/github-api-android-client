package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.Repo

interface RepoListRepository {
    suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody>
}