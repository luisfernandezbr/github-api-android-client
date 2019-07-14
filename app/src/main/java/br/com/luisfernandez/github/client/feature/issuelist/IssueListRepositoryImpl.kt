package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.mvvm.repository.api.GitHubService
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.http.DeferredResponseHandler
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse

class IssueListRepositoryImpl(
        private val gitHubService: GitHubService
) : IssueListRepository {
    override suspend fun loadIssueList(owner: String, repoName: String): ResultWrapper<List<IssueResponse>, GitHubErrorBody> {
        return DeferredResponseHandler().handle(gitHubService.listIssuesAsync(owner, repoName))
    }
}