package br.com.luisfernandez.github.client.repolist.model

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.dto.RepoDTO
import io.reactivex.Observable

class RepoListModelImpl (
    private val gitHubService: GitHubService
) : RepoListModel
{
    override fun loadRepoList(page: Int, language: String): Observable<List<RepoDTO>> {
        return gitHubService
                .listRepos(page, "language:${language}")
                .map { repoListResponse ->
                    repoListResponse.repoDTOList
                }
    }
}