package br.com.luisfernandez.github.client.repolist

import android.util.Log
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenterImpl : HomePresenter {

    private lateinit var view: HomeView

    override fun inject(view: HomeView) {
        this.view = view
    }

    override fun loadRepoList(page: Int) {

        RepoListModelImpl()
                .loadRepoList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<Repo>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        Log.d("HOME_ACTIVITY", "onError(${error?.errorBody?.message}) (${error.httpStatus})")
                        view.handleError(error)
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        view.showRepoList(repoList)
                    }
                })
    }
}