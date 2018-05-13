package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.model.PullRequestResponse
import io.reactivex.Observable
import javax.inject.Inject

class PullRequestModelImpl @Inject constructor(
    private val gitHubService: GitHubService
) : PullRequestModel
{
    override fun loadPullRequestList(owner: String, repoName: String): Observable<List<PullRequestResponse>> {
        return gitHubService
                .listPullRequests(owner, repoName)
                .map { pullRequestList ->
                    pullRequestList
                }
    }
}