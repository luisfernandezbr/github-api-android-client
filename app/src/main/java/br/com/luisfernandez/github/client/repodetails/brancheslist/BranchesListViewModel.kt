package br.com.luisfernandez.github.client.repodetails.brancheslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.BranchResponse
import br.com.luisfernandez.github.client.repodetails.contributorslist.BranchesListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BranchesListViewModel (
    private val branchesListModel: BranchesListModel
) : ViewModel()
{

    val branchesList = MutableLiveData<List<BranchResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadContributorsList(owner: String, repoName: String) {
        branchesListModel
                .loadBranchesList(owner, repoName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<BranchResponse>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(listBranches: List<BranchResponse>) {
                        branchesList.postValue(listBranches)
                    }
                })
    }
}