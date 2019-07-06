package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.pullrequest.PullRequestPresenter
import br.com.luisfernandez.github.client.pullrequest.PullRequestPresenterImpl
import org.koin.dsl.module.module

val presenterModule = module {
    single<PullRequestPresenter> {
        PullRequestPresenterImpl(get())
    }
}
