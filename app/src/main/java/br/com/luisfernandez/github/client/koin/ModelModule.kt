package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.issuelist.IssueListModel
import br.com.luisfernandez.github.client.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListRepository
import org.koin.dsl.module.module

val modelModule = module {

    single<RepoListModel> {
        RepoListModelImpl(
                get() as GitHubService,
                get() as RepoListRepository
        )
    }

    single<PullRequestModel> {
        PullRequestModelImpl(
                get()
        )
    }

    single<IssueListModel> {
        IssueListModelImpl(
                get()
        )
    }
}