package br.com.luisfernandez.github.client.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PullRequestViewModel (
    private val pullRequestModel: PullRequestModel
) : ViewModel()
{

    val listPullRequest = MutableLiveData<List<PullRequestResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadPullRequestList(owner: String, repoName: String) {
        pullRequestModel
                .loadPullRequestList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<PullRequestResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(pullRequestList: List<PullRequestResponse>) {
                        listPullRequest.postValue(pullRequestList)
                    }
                })
    }
}