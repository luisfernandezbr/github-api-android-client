package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo

class RepoListModelImpl (
    private val repoListRepository: RepoListRepository
) : RepoListModel
{
    override suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody> {
        return repoListRepository.loadRepoListCoroutineAsync(page, language)
    }
}