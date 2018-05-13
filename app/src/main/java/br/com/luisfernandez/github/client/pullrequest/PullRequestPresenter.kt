package br.com.luisfernandez.github.client.pullrequest

/**
 * Created by luisfernandez on 12/05/18.
 */
interface PullRequestPresenter {
    fun loadPullRequestList(owner: String, repoName: String)
    fun inject(pullRequestView: PullRequestListView)
}