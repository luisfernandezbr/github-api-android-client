package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.issuelist.IssueListModel
import br.com.luisfernandez.github.client.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.model.RepoListModel
import br.com.luisfernandez.github.client.repolist.model.RepoListModelImpl
import org.koin.dsl.module.module

val modelModule = module {

    single<RepoListModel> {
        RepoListModelImpl(
                get()
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