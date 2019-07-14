package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.pullrequest.PullRequestRepository
import br.com.luisfernandez.github.client.pullrequest.PullRequestRepositoryImpl
import br.com.luisfernandez.github.client.repolist.RepoListRepository
import br.com.luisfernandez.github.client.repolist.RepoListRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {

    single<RepoListRepository> {
        RepoListRepositoryImpl(
            get() as GitHubService
        )
    }

    single<PullRequestRepository> {
        PullRequestRepositoryImpl(
            get() as GitHubService
        )
    }
}