package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListModelImpl
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
}