package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse

interface IssueListRepository {
    suspend fun loadIssueList(owner: String, repoName: String): ResultWrapper<List<IssueResponse>, GitHubErrorBody>
}