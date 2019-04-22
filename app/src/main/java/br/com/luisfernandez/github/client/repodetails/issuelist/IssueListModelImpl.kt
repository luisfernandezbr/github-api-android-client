package br.com.luisfernandez.github.client.repodetails.issuelist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.IssueResponse
import io.reactivex.Observable

class IssueListModelImpl (
    private val gitHubService: GitHubService
) : IssueListModel
{
    override fun loadIssueList(owner: String, repoName: String): Observable<List<IssueResponse>> {
        return gitHubService
                .listIssues(owner, repoName)
                .map { issueList ->
                    issueList
                }
    }
}