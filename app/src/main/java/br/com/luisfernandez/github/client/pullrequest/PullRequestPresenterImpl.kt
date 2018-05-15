package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PullRequestPresenterImpl @Inject constructor(
    private val pullRequestModel: PullRequestModel
) : PullRequestPresenter
{

    private lateinit var view: PullRequestListView

    override fun inject(view: PullRequestListView) {
        this.view = view
    }

    override fun loadPullRequestList(owner: String, repoName: String) {
        pullRequestModel
                .loadPullRequestList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<PullRequestResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        view.handleError(error)
                    }

                    override fun onSuccess(pullRequestList: List<PullRequestResponse>) {
                        view.showContent(pullRequestList)
                    }
                })
    }
}