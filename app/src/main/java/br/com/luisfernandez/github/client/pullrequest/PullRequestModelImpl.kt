package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import io.reactivex.Observable

class PullRequestModelImpl (
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