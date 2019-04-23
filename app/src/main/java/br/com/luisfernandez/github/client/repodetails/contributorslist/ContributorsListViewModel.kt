package br.com.luisfernandez.github.client.repodetails.contributorslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.ContributorResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContributorsListViewModel (
    private val contributorsListModel: ContributorsListModel
) : ViewModel()
{

    val contributorsList = MutableLiveData<List<ContributorResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadContributorsList(owner: String, repoName: String) {
        contributorsListModel
                .loadContributorsList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<ContributorResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(listContributors: List<ContributorResponse>) {
                        contributorsList.postValue(listContributors)
                    }
                })
    }
}