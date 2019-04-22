@file:Suppress("RemoveExplicitTypeArguments")

package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repodetails.commitslist.CommitsListViewModel
import br.com.luisfernandez.github.client.repodetails.contributorslist.ContributorsListViewModel
import br.com.luisfernandez.github.client.repodetails.issuelist.IssueListViewModel
import br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestViewModel
import br.com.luisfernandez.github.client.repolist.RepoListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel<RepoListViewModel> {
        RepoListViewModel(
                get()
        )
    }

    viewModel<PullRequestViewModel> {
        PullRequestViewModel(
                get()
        )
    }

    viewModel<IssueListViewModel> {
        IssueListViewModel(
                get()
        )
    }

    viewModel<ContributorsListViewModel> {
        ContributorsListViewModel(
                get()
        )
    }

    viewModel<CommitsListViewModel> {
        CommitsListViewModel(
                get()
        )
    }

}