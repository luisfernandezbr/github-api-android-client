package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.*
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

    override suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<RepoListResponse, String> {
        return repoListRepository.loadRepoListCoroutineAsync(page, language)
    }

    override suspend fun loadRepoListCoroutineWrapped(page: Int, language: String): ResultWrapper<List<Repo>, String> {
        val loadRepoListCoroutineAsync = repoListRepository.loadRepoListCoroutineAsync(page, language)

        val transformerAdapter = object : ResultWrapperAdapter<RepoListResponse, String, List<Repo>, String>() {
            override fun adaptKeyValueMap(fromKeyValueMap: MutableMap<String, String>): MutableMap<String, String>? {
                val toKeyValueMap = HashMap<String, String>(1)

                val keyCacheControl = "Cache-Control"

                if (fromKeyValueMap.containsKey(keyCacheControl)) {
                    fromKeyValueMap[keyCacheControl]?.run {
                        toKeyValueMap[keyCacheControl] = this
                    }
                }

                return toKeyValueMap
            }

            override fun adaptSuccess(fromSuccess: RepoListResponse?): List<Repo>? {
                return fromSuccess?.repos
            }

            override fun adaptError(fromError: String?): String? {
                return fromError
            }
        }

        return transformerAdapter.adapt(loadRepoListCoroutineAsync)
    }
}