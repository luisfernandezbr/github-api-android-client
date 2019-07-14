package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.ErrorData
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapperAdapter
import br.com.luisfernandez.github.client.mvvm.repository.api.GitHubService
import br.com.luisfernandez.github.client.mvvm.repository.http.DeferredResponseHandler
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo
import br.com.luisfernandez.github.client.mvvm.repository.pojo.RepoListResponse

class RepoListRepositoryImpl(
        private val gitHubService: GitHubService
) : RepoListRepository {

    override suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<List<Repo>, ErrorData<GitHubErrorBody>> {
        val resultWrapper = DeferredResponseHandler().handle<RepoListResponse, GitHubErrorBody>(
                deferredResponse = gitHubService.listReposCoroutineAsync(page, language)
        )
        return this.getResultWrapperAdapter().adapt(resultWrapper)
    }

    private fun getResultWrapperAdapter(): ResultWrapperAdapter<RepoListResponse, ErrorData<GitHubErrorBody>, List<Repo>, ErrorData<GitHubErrorBody>> {
        return object : ResultWrapperAdapter<RepoListResponse, ErrorData<GitHubErrorBody>, List<Repo>, ErrorData<GitHubErrorBody>>() {

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

            override fun adaptError(fromError: ErrorData<GitHubErrorBody>?): ErrorData<GitHubErrorBody>? {
                return fromError
            }
        }
    }
}