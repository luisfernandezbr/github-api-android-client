package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.model.Repo
import io.reactivex.Observable
import javax.inject.Inject


class RepoListModelImpl @Inject constructor(
    private val gitHubService: GitHubService
) : RepoListModel
{
    override fun loadRepoList(page: Int): Observable<List<Repo>> {
        return gitHubService
                .listRepos(page)
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }
}