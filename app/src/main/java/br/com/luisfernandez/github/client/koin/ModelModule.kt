package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.livedata.AppExecutors
import br.com.luisfernandez.github.client.issuelist.IssueListModel
import br.com.luisfernandez.github.client.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListLiveDataModel
import br.com.luisfernandez.github.client.repolist.RepoListLiveDataModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListModelImpl
import org.koin.dsl.module.module

val modelModule = module {

    single<RepoListModel> {
        RepoListModelImpl(
                get()
        )
    }

    single<RepoListLiveDataModel> {
        RepoListLiveDataModelImpl(
                get() as AppExecutors,
                get() as GitHubService
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