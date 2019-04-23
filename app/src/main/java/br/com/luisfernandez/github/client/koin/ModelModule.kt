package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.repodetails.brancheslist.BranchesListModelImpl
import br.com.luisfernandez.github.client.repodetails.brancheslist.BranchesListModel
import br.com.luisfernandez.github.client.repodetails.commitslist.CommitsListModel
import br.com.luisfernandez.github.client.repodetails.commitslist.CommitsListModelImpl
import br.com.luisfernandez.github.client.repodetails.contributorslist.ContributorsListModel
import br.com.luisfernandez.github.client.repodetails.contributorslist.ContributorsListModelImpl
import br.com.luisfernandez.github.client.repodetails.issuelist.IssueListModel
import br.com.luisfernandez.github.client.repodetails.issuelist.IssueListModelImpl
import br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.repodetails.pullrequest.PullRequestModelImpl
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

    single<IssueListModel> {
        IssueListModelImpl(
                get()
        )
    }

    single<ContributorsListModel> {
        ContributorsListModelImpl(
                get()
        )
    }

    single<CommitsListModel> {
        CommitsListModelImpl(
                get()
        )
    }

    single<BranchesListModel> {
        BranchesListModelImpl(
                get()
        )
    }
}