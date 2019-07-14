package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repolist.RepoListRepository
import br.com.luisfernandez.github.client.repolist.RepoListRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {

    single<RepoListRepository> {
        RepoListRepositoryImpl(
                get()
        )
    }
}