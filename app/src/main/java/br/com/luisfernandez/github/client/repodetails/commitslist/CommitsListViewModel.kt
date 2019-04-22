package br.com.luisfernandez.github.client.repodetails.commitslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.CommitResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommitsListViewModel (
    private val commitsListModel: CommitsListModel
) : ViewModel()
{

    val commitsList = MutableLiveData<List<CommitResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadCommitsList(owner: String, repoName: String) {
        commitsListModel
                .loadCommitsList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<CommitResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(listCommits: List<CommitResponse>) {
                        commitsList.postValue(listCommits)
                    }
                })
    }
}