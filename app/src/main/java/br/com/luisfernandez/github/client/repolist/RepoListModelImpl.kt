package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.*
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoListModelImpl (
    private val repoListRepository: RepoListRepository
) : RepoListModel
{
    override suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody> {
        return repoListRepository.loadRepoListCoroutineAsync(page, language)
    }
}