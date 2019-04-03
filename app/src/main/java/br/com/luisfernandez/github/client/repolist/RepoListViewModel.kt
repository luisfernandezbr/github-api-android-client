package br.com.luisfernandez.github.client.repolist

import android.arch.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val repoListModel: RepoListModel
) : ViewModel() {

    private lateinit var view: RepoListView

    fun loadRepoList(page: Int, language: String) {

        repoListModel
                .loadRepoList(page, language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<Repo>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        view.handleError(error)
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        view.showContent(repoList)
                    }
                })
    }
}