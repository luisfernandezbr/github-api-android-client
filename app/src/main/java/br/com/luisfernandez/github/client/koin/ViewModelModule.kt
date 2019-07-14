@file:Suppress("RemoveExplicitTypeArguments")

package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.feature.issuelist.IssueListViewModel
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestRepository
import br.com.luisfernandez.github.client.feature.pullrequest.PullRequestViewModel
import br.com.luisfernandez.github.client.feature.repolist.RepoListModel
import br.com.luisfernandez.github.client.feature.repolist.RepoListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel<RepoListViewModel> {
        RepoListViewModel(
                get() as RepoListModel
        )
    }

    viewModel<PullRequestViewModel> {
        PullRequestViewModel(
                get() as PullRequestRepository
        )
    }

    viewModel<IssueListViewModel> {
        IssueListViewModel(
                get()
        )
    }
}