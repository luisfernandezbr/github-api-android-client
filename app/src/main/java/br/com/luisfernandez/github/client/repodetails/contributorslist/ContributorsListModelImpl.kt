package br.com.luisfernandez.github.client.repodetails.contributorslist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.ContributorResponse
import io.reactivex.Observable

class ContributorsListModelImpl (
    private val gitHubService: GitHubService
) : ContributorsListModel
{
    override fun loadContributorList(owner: String, repoName: String): Observable<List<ContributorResponse>> {
        return gitHubService
                .listContributors(owner, repoName)
                .map { contributorList ->
                    contributorList
                }
    }
}