package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.api.GitHubService
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.v2.jw.DeferredResponseHandler
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.pojo.IssueResponse

class IssueListRepositoryImpl(
        private val gitHubService: GitHubService
) : IssueListRepository {
    override suspend fun loadIssueList(owner: String, repoName: String): ResultWrapper<List<IssueResponse>, GitHubErrorBody> {
        return DeferredResponseHandler().handle(gitHubService.listIssues(owner, repoName))
    }
}