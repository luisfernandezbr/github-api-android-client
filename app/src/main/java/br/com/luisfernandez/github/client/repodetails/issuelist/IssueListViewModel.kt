package br.com.luisfernandez.github.client.repodetails.issuelist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.IssueResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IssueListViewModel (
    private val issueListModel: IssueListModel
) : ViewModel()
{

    val issueList = MutableLiveData<List<IssueResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadIssueList(owner: String, repoName: String) {
        issueListModel
                .loadIssueList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<IssueResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(listIssue: List<IssueResponse>) {
                        issueList.postValue(listIssue)
                    }
                })
    }
}