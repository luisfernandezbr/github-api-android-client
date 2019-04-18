package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repodetails.issuelist.IssueListModel
import br.com.luisfernandez.github.client.repodetails.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListModelImpl
import org.koin.dsl.module.module

val modelModule = module {

    single<RepoListModel> {
        RepoListModelImpl(
                get()
        )
    }

    single<br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModel> {
        br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModelImpl(
                get()
        )
    }

    single<IssueListModel> {
        IssueListModelImpl(
                get()
        )
    }
}