package br.com.luisfernandez.github.client

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.luisfernandez.github.client.http.CallbackRequest
import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.model.RepoListResponse
import br.com.luisfernandez.github.client.model.Result
import kotlinx.android.synthetic.main.activity_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



@EActivity(R.layout.activity_home)
class HomeActivity : AppCompatActivity() {

    @AfterViews
    fun afterViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(GitHubService::class.java)
        service.listRepos().enqueue(object: CallbackRequest<RepoListResponse>() {
            override fun success(response: Response<RepoListResponse>) {
                Log.d("", "")
            }

            override fun failureHttp(response: Response<RepoListResponse>) {

            }

            override fun failure(throwable: Throwable) {

            }
        })

    }
}
