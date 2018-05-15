package br.com.luisfernandez.github.client.pullrequest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.luisfernandez.github.client.OnItemClickListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import javax.inject.Inject

@SuppressLint("Registered")
@EActivity(R.layout.activity_pull_request_list)
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
        this.configToolbar()

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        presenter.inject(this)
        presenter.loadPullRequestList(owner, repoName)
    }

    private fun configToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            title = repoName
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun handleError(serverError: ServerError<GitHubErrorBody>) {
        this.showErrorState()

        if (serverError.errorBody != null) {
            textErrorMessage.text = serverError.errorBody!!.message
        } else {
            textErrorMessage.text = serverError.errorMessage
        }

        buttonRetry.setOnClickListener { _ ->
            showLoading()
            presenter.loadPullRequestList(owner, repoName)
        }
    }

    private fun showErrorState() {
        layoutProgress.setGone()
        layoutEmpty.setGone()
        layoutError.setVisible()
        recyclerView.setGone()
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
            recyclerView.setVisible()

            recyclerView.adapter = this.getPullRequestListAdapter(content)
        }
    }

    private fun getPullRequestListAdapter(content: List<PullRequestResponse>): PullRequestListAdapter {
        return PullRequestListAdapter(
                content as ArrayList<PullRequestResponse>,
                getOnItemClickListener()
        )
    }

    private fun getOnItemClickListener(): OnItemClickListener<PullRequestResponse> {
        return object : OnItemClickListener<PullRequestResponse> {
            override fun onItemClick(type: PullRequestResponse) {
                openOnBrowser(type.htmlUrl)
            }
        }
    }

    private fun openOnBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
