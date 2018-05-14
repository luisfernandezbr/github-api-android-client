package br.com.luisfernandez.github.client.pullrequest

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.PullRequestResponse
import br.com.luisfernandez.github.client.model.Repo
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import javax.inject.Inject

@SuppressLint("Registered")
@EActivity(R.layout.activity_list)
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

    @AfterViews
    fun afterViews() {
        val recyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        presenter.inject(this)
        presenter.loadPullRequestList(owner, repoName)
    }

    override fun handleError(serverError: ServerError<GitHubErrorBody>) {
        layoutProgress.setGone()
        layoutEmpty.setGone()
        layoutError.setVisible()
        recyclerView.setGone()

        textErrorMessage.text = serverError.errorBody!!.message

        buttonRetry.setOnClickListener { _ ->
            showLoading()
            presenter.loadPullRequestList(owner, repoName)
        }
    }

    override fun showLoading() {
        layoutProgress.setVisible()
        layoutEmpty.setGone()
        layoutError.setGone()
        recyclerView.setGone()
    }

    override fun showEmpty() {
        layoutProgress.setGone()
        layoutEmpty.setVisible()
        layoutError.setGone()
        recyclerView.setGone()
    }

    override fun showContent(content: List<PullRequestResponse>) {

        if (content.isEmpty()) {
            showEmpty()
        } else {
            layoutProgress.setGone()
            layoutEmpty.setGone()
            layoutError.setGone()
            recyclerView.adapter = PullRequestListAdapter(content as ArrayList<PullRequestResponse>)
            recyclerView.setVisible()
        }
    }
}
