package br.com.luisfernandez.github.client

import android.util.Log
import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ServiceFactory
import br.com.luisfernandez.github.client.model.RepoListResponse
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HomePresenterImpl : HomePresenter {

    private lateinit var view: HomeView

    override fun inject(view: HomeView) {
        this.view = view
    }

    override fun loadRepoList() {
        val retrofitService = ServiceFactory.createRetrofitService(GitHubService::class.java)
        val listRepos = retrofitService.listRepos()
        listRepos.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<RepoListResponse>() {
                    override fun onCompleted() {
                        Log.d("HOME_ACTIVITY", "onCompleted()")
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            Log.d("HOME_ACTIVITY", "${e.code()} + onError() ${e.response().errorBody()?.string()} ")
                        } else {
                            Log.d("HOME_ACTIVITY", "onError() ${e.message}")
                        }
                    }

                    override fun onNext(repoListResponse: RepoListResponse) {
                        Log.d("HOME_ACTIVITY", "onNext() + ${repoListResponse.totalCount}")
                        view.showRepoList(repoListResponse.repos)
                    }
                })
    }
}