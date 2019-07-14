package br.com.luisfernandez.github.client.feature.issuelist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.ServerError
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("Registered")
@EActivity(R.layout.activity_issues_list)
class IssueListActivity : AppCompatActivity(), IssueListView {

    @Extra
    lateinit var owner: String

    @Extra
    lateinit var repoName: String

    val viewModel by viewModel<IssueListViewModel>()

    @AfterViews
    fun afterViews() {
        this.configToolbar()

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        setupViewModel()

        viewModel.loadIssueList(owner, repoName)
    }

    private fun setupViewModel() {
        viewModel.issueList.observe(this, Observer { issueList ->
            showContent(issueList!!)
        })

        viewModel.serverError.observe(this, Observer { serverError ->
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
            viewModel.loadIssueList(owner, repoName)
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

    override fun showContent(content: List<IssueResponse>) {
        if (content.isEmpty()) {
            showEmpty()
        } else {
            layoutProgress.setGone()
            layoutEmpty.setGone()
            layoutError.setGone()
            recyclerView.setVisible()

            recyclerView.adapter = this.getIssueListAdapter(content)
        }
    }

    private fun getIssueListAdapter(content: List<IssueResponse>): IssueListAdapter {
        return IssueListAdapter(
                content as ArrayList<IssueResponse>
        )
    }
}
