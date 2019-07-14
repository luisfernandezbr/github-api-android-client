package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.PullRequestResponse

interface PullRequestRepository {
    suspend fun loadPullRequestList(owner: String, repoName: String): ResultWrapper<List<PullRequestResponse>, GitHubErrorBody>
}