@file:Suppress("RemoveExplicitTypeArguments")

package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repolist.RepoListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel<RepoListViewModel> {
        RepoListViewModel(
                get()
        )
    }

}