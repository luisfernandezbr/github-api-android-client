package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ServiceFactory
import br.com.luisfernandez.github.client.model.PullRequestResponse
import io.reactivex.Observable

class PullRequestModelImpl : PullRequestModel {
    override fun loadPullRequestList(owner: String, repoName: String): Observable<List<PullRequestResponse>> {
        val retrofitService = ServiceFactory.createRetrofitService(GitHubService::class.java)
        return retrofitService
                .listPullRequests(owner, repoName)
                .map { pullRequestList ->
                    pullRequestList
                }
    }
}