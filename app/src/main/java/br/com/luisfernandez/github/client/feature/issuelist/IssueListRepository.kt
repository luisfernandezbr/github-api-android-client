package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.pojo.IssueResponse

interface IssueListRepository {
    suspend fun loadIssueList(owner: String, repoName: String): ResultWrapper<List<IssueResponse>, GitHubErrorBody>
}