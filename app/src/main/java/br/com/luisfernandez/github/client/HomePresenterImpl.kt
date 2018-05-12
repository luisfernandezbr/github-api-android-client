package br.com.luisfernandez.github.client

import android.util.Log
import br.com.luisfernandez.github.client.model.Repo
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
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
                .subscribeWith(object: CallbackWrapper<List<Repo>>() {
                    override fun onProblem(errorWrapper: ErrorWrapper?) {
                        val e = errorWrapper!!.throwable

                        if (e is HttpException) {
                            Log.d("HOME_ACTIVITY", "${e.code()} + onError() ${e.response().errorBody()?.string()} ")
                        } else {
                            Log.d("HOME_ACTIVITY", "onError() ${e.message}")
                        }

                        view.showError()
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        view.showRepoList(repoList)
                    }
                })


//                .subscribe(object : Subscriber<List<Repo>>() {
//                    override fun onCompleted() {
//                        Log.d("HOME_ACTIVITY", "onCompleted()")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        if (e is HttpException) {
//                            Log.d("HOME_ACTIVITY", "${e.code()} + onError() ${e.response().errorBody()?.string()} ")
//                        } else {
//                            Log.d("HOME_ACTIVITY", "onError() ${e.message}")
//                        }
//
//                        view.showError()
//                    }
//
//                    override fun onNext(repoList: List<Repo>) {
//                        Log.d("HOME_ACTIVITY", "onNext() + ${repoList.size}")
//                        view.showRepoList(repoList)
//                    }
//                })
    }
}