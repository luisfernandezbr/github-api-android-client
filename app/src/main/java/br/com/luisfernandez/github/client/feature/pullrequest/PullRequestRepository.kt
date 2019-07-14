package br.com.luisfernandez.github.client.feature.pullrequest

import br.com.luisfernandez.github.client.mvvm.repository.ErrorData
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.PullRequestResponse

interface PullRequestRepository {
    suspend fun loadPullRequestList(owner: String, repoName: String): ResultWrapper<List<PullRequestResponse>, ErrorData<GitHubErrorBody>>
}