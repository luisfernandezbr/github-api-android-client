package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.Observable

class RepoListModelImpl (
    private val gitHubService: GitHubService
) : RepoListModel
{
    override fun loadRepoList(page: Int, language: String): Observable<List<Repo>> {
        return gitHubService
                .listRepos(page, "language:${language}")
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }
}