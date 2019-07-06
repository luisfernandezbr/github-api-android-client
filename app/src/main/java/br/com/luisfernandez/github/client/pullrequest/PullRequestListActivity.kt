package br.com.luisfernandez.github.client.pullrequest

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import br.com.luisfernandez.github.client.repolist.view.OnItemClickListener
import br.com.luisfernandez.github.client.R
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
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("Registered")
@EActivity(R.layout.activity_pull_request_list)
class PullRequestListActivity : AppCompatActivity(), PullRequestListView {

    @Extra
    lateinit var owner: String

    @Extra
    lateinit var repoName: String

    val viewModel by viewModel<PullRequestViewModel>()

    @AfterViews
    fun afterViews() {
        this.configToolbar()

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//
        setupViewModel()

        viewModel.loadPullRequestList(owner, repoName)
    }

    private fun setupViewModel() {
        viewModel.listPullRequest.observe(this, Observer {
            listPullRequest ->
            showContent(listPullRequest!!)
        })

        viewModel.serverError.observe(this, Observer {
            serverError ->
            handleError(serverError!!)
        })
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
            viewModel.loadPullRequestList(owner, repoName)
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
