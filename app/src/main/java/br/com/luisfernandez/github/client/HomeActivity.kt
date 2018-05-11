package br.com.luisfernandez.github.client

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.com.luisfernandez.github.client.android.RepoListAdapter
import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ServiceFactory
import br.com.luisfernandez.github.client.model.RepoListResponse
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@SuppressLint("Registered")
@EActivity(R.layout.activity_home)
class HomeActivity : AppCompatActivity() {

    @AfterViews
    fun afterViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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
                        ensureUi(repoListResponse)
                    }
                })
    }

    fun ensureUi(body: RepoListResponse?) {
        val recyclerView = recyclerView

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RepoListAdapter(body!!.repos)
    }
}
