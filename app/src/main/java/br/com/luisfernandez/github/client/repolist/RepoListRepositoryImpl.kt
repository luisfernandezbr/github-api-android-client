package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.http.ResultWrapperAdapter
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.v2.jw.DeferredResponseHandler
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse

class RepoListRepositoryImpl(
        private val gitHubService: GitHubService
) : RepoListRepository {

    override suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody> {
        val resultWrapper = DeferredResponseHandler().handle<RepoListResponse, GitHubErrorBody>(
                deferredResponse = gitHubService.listReposCoroutine(page, language)
        )
        return this.getResultWrapperAdapter().adapt(resultWrapper)
    }

    private fun getResultWrapperAdapter(): ResultWrapperAdapter<RepoListResponse, GitHubErrorBody, List<Repo>, GitHubErrorBody> {
        return object : ResultWrapperAdapter<RepoListResponse, GitHubErrorBody, List<Repo>, GitHubErrorBody>() {

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

            override fun adaptError(fromError: GitHubErrorBody?): GitHubErrorBody? {
                return fromError
            }
        }
    }
}