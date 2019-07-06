package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val repoListModel: RepoListModel
) : ViewModel() {

    val listRepo = MutableLiveData<List<Repo>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadRepoList(page: Int, language: String) {

        repoListModel
                .loadRepoList(page, language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<Repo>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        listRepo.postValue(repoList)
                    }
                })
    }
}