package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.*
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoListModelImpl (
    private val gitHubService: GitHubService,
    private val repoListRepository: RepoListRepository
) : RepoListModel
{
    override fun loadRepoList(page: Int, language: String): Observable<List<Repo>> {
        return gitHubService
                .listRepos(page, "language:${language}")
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }

    override suspend fun loadRepoListCoroutineAsync(page: Int, language: String): List<Repo> {
        return withContext(Dispatchers.IO) {
            var result = ArrayList<Repo>()

            try {
                val listReposCoroutine = gitHubService.listReposCoroutine(page, language).await()
                val repoListResponse = listReposCoroutine.body() as RepoListResponse
                result = repoListResponse.repos as ArrayList<Repo>
            } catch(e: Exception) {

            }

            result
        }
    }

    override suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody> {
        return repoListRepository.loadRepoListCoroutineAsync(page, language)
    }
}