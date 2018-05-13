package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.ErrorHandlerView
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.model.PullRequestResponse
import br.com.luisfernandez.github.client.model.Repo

/**
 * Created by luisfernandez on 11/05/18.
 */
interface PullRequestListView : ErrorHandlerView<GitHubErrorBody> {
    fun showPullRequestList(repoList: List<PullRequestResponse>)
    fun showProgress()
    fun showError()
}