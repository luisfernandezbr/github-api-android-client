package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.repolist.repository.RepoListRepository
import br.com.luisfernandez.github.client.repolist.repository.RepoListRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {
    single<RepoListRepository> {
        RepoListRepositoryImpl(
                get() as GitHubService
        )
    }
}
