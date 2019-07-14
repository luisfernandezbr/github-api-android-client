package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.ErrorData
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo

interface RepoListRepository {
    suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<List<Repo>, ErrorData<GitHubErrorBody>>
}