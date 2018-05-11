package br.com.luisfernandez.github.client

import android.util.Log
import br.com.luisfernandez.github.client.model.Repo
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
                .subscribe(object : Subscriber<List<Repo>>() {
                    override fun onCompleted() {
                        Log.d("HOME_ACTIVITY", "onCompleted()")
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            Log.d("HOME_ACTIVITY", "${e.code()} + onError() ${e.response().errorBody()?.string()} ")
                        } else {
                            Log.d("HOME_ACTIVITY", "onError() ${e.message}")
                        }

                        view.showError()
                    }

                    override fun onNext(repoList: List<Repo>) {
                        Log.d("HOME_ACTIVITY", "onNext() + ${repoList.size}")
                        view.showRepoList(repoList)
                    }
                })
    }
}