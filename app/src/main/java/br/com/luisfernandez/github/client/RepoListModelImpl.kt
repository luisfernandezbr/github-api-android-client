package br.com.luisfernandez.github.client

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ServiceFactory
import br.com.luisfernandez.github.client.model.Repo
import io.reactivex.Observable


class RepoListModelImpl : RepoListModel {
    override fun loadRepoList(page: Int): Observable<List<Repo>> {
        val retrofitService = ServiceFactory.createRetrofitService(GitHubService::class.java)
        return retrofitService
                .listRepos(page)
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }
}