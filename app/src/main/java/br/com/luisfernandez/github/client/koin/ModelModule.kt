package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.feature.issuelist.IssueListModel
import br.com.luisfernandez.github.client.feature.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.feature.repolist.RepoListModel
import br.com.luisfernandez.github.client.feature.repolist.RepoListModelImpl
import br.com.luisfernandez.github.client.feature.repolist.RepoListRepository
import org.koin.dsl.module.module

val modelModule = module {

    single<RepoListModel> {
        RepoListModelImpl(
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