package br.com.luisfernandez.github.client.repolist

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.RepoListAdapter
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.Repo
import kotlinx.android.synthetic.main.activity_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_home)
class HomeActivity : AppCompatActivity(), HomeView {

    override fun handleError(serverError: ServerError<GitHubErrorBody>) {

        if (serverError.errorBody != null) {
            Toast.makeText(this, "HTTP STATUS: ${serverError.httpStatus}\nMESSAGE${serverError.errorBody!!.message} ", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "HTTP STATUS: ${serverError.httpStatus}\nMESSAGE${serverError.errorMessage} ", Toast.LENGTH_LONG).show()
        }

        var progressBar = progressBar
        progressBar.setGone()
    }

    override fun showError() {
        Toast.makeText(this, "ERRORRRRR", Toast.LENGTH_LONG).show()

        var progressBar = progressBar
        progressBar.setGone()
    }

    override fun showProgress() {
        var progressBar = progressBar
        progressBar.setVisible()

        var recyclerView = recyclerView
        recyclerView.setGone()
    }

    private lateinit var presenter: HomePresenter

    @AfterViews
    fun afterViews() {
        presenter = HomePresenterImpl()
        presenter.inject(this)
        presenter.loadRepoList(50)
    }

    override fun showRepoList(repoList: List<Repo>) {
        val recyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RepoListAdapter(repoList)

        var progressBar = progressBar
        progressBar.setGone()

        recyclerView.setVisible()
    }
}
