package br.com.luisfernandez.github.client.feature.pullrequest

import br.com.luisfernandez.github.client.api.GitHubService
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.v2.jw.DeferredResponseHandler
import br.com.luisfernandez.github.client.pojo.PullRequestResponse

class PullRequestRepositoryImpl(
        private val gitHubService: GitHubService
) : PullRequestRepository {
    override suspend fun loadPullRequestList(owner: String, repoName: String): ResultWrapper<List<PullRequestResponse>, GitHubErrorBody> {
        return DeferredResponseHandler().handle(gitHubService.listPullRequestsAsync(owner, repoName))
    }
}