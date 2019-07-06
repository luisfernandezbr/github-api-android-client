package br.com.luisfernandez.github.client.repolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.dto.RepoDTO
import br.com.luisfernandez.github.client.repolist.model.RepoListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val repoListModel: RepoListModel
) : ViewModel() {

    val listRepo = MutableLiveData<List<RepoDTO>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadRepoList(page: Int, language: String) {

        repoListModel
                .loadRepoList(page, language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<RepoDTO>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(repoDTOList: List<RepoDTO>) {
                        listRepo.postValue(repoDTOList)
                    }
                })
    }
}