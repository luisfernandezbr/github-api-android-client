package br.com.luisfernandez.github.client.repolist

import android.util.Log
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoListPresenterImpl @Inject constructor(
        private val repoListModel: RepoListModel
) : RepoListPresenter {

    private lateinit var view: RepoListView

    override fun inject(repoListView: RepoListView) {
        this.view = repoListView
    }

    override fun loadRepoList(page: Int) {

        repoListModel
                .loadRepoList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<Repo>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        Log.d("HOME_ACTIVITY", "onError(${error?.errorBody?.message}) (${error.httpStatus})")
                        view.handleError(error)
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        view.showContent(repoList)
                    }
                })
    }
}