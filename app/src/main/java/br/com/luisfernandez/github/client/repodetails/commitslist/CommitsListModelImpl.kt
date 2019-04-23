package br.com.luisfernandez.github.client.repodetails.commitslist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.commit.CommitResponse
import io.reactivex.Observable

class CommitsListModelImpl (
    private val gitHubService: GitHubService
) : CommitsListModel
{
    override fun loadCommitsList(owner: String, repoName: String): Observable<List<CommitResponse>> {
        return gitHubService
                .listCommits(owner, repoName)
                .map { commitsList ->
                    commitsList
                }
    }
}