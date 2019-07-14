package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.api.GitHubService
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestRepository
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestRepositoryImpl
import br.com.luisfernandez.github.client.feature.repolist.RepoListRepository
import br.com.luisfernandez.github.client.feature.repolist.RepoListRepositoryImpl
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