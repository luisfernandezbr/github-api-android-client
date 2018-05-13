package br.com.luisfernandez.github.client.pullrequest

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.PullRequestResponse
import kotlinx.android.synthetic.main.activity_repo_list.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import javax.inject.Inject


@SuppressLint("Registered")
@EActivity(R.layout.activity_repo_list)
class PullRequestListActivity : AppCompatActivity(), PullRequestListView {

    init {
        AppApplication.component.inject(this)
    }

    @Extra
    lateinit var owner: String

    @Extra
    lateinit var repoName: String

    @Inject
    lateinit var presenter: PullRequestPresenter

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

    @AfterViews
    fun afterViews() {
        presenter.inject(this)
        presenter.loadPullRequestList(owner, repoName)
    }

    override fun showPullRequestList(repoList: List<PullRequestResponse>) {
        val recyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = PullRequestListAdapter(repoList as ArrayList<PullRequestResponse>)

        val progressBar = progressBar
        progressBar.setGone()
        recyclerView.setVisible()
    }
}
