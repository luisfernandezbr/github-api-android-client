package br.com.luisfernandez.github.client.repodetails.brancheslist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.BranchResponse
import io.reactivex.Observable

class BranchesListModelImpl (
    private val gitHubService: GitHubService
) : BranchesListModel
{
    override fun loadBranchesList(owner: String, repoName: String): Observable<List<BranchResponse>> {
        return gitHubService
                .listBranches(owner, repoName)
                .map { branchesList ->
                    branchesList
                }
    }
}