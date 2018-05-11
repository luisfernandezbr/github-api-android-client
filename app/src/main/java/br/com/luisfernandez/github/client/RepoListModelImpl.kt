package br.com.luisfernandez.github.client

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ServiceFactory
import br.com.luisfernandez.github.client.model.Repo
import rx.Observable


class RepoListModelImpl : RepoListModel {
    override fun loadRepoList(): Observable<List<Repo>> {
        val retrofitService = ServiceFactory.createRetrofitService(GitHubService::class.java)
        return retrofitService
                .listRepos()
                .map { repoListResponse ->
                    repoListResponse.repos
                }
    }
}