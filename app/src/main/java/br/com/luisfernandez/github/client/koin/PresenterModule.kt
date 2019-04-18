package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repolist.RepoListPresenter
import br.com.luisfernandez.github.client.repolist.RepoListPresenterImpl
import org.koin.dsl.module.module

val presenterModule = module {
    single<RepoListPresenter> {
        RepoListPresenterImpl(get())
    }
}
