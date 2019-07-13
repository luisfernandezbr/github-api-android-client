package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoListModelImpl (
    private val gitHubService: GitHubService
) : RepoListModel
{
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

    override fun loadRepoList(page: Int, language: String): Observable<List<Repo>> {
        return gitHubService
                .listRepos(page, "language:${language}")
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }
}