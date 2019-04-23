package br.com.luisfernandez.github.client.repodetails.issuelist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.IssueResponse
import br.com.luisfernandez.github.client.repodetails.RepoDetailsPagerAdapter.Companion.OWNER
import br.com.luisfernandez.github.client.repodetails.RepoDetailsPagerAdapter.Companion.REPONAME
import kotlinx.android.synthetic.main.fragment_issues_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class IssuesListFragment : Fragment() {

    lateinit var owner: String
    lateinit var repoName: String
    val viewModel by viewModel<IssueListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_issues_list, container, false)

        val recyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(rootView.context)
        recyclerView.setHasFixedSize(true)

        setupArguments()

        setupViewModel()

        viewModel.loadIssueList(owner, repoName)

        return rootView

    }

    private fun setupArguments() {
        owner = arguments!!.getString(OWNER)
        repoName = arguments!!.getString(REPONAME)
    }

    private fun setupViewModel() {
        viewModel.issueList.observe(this, Observer {
            issueList ->
            showContent(issueList!!)
        })

        viewModel.serverError.observe(this, Observer {
            serverError ->
            handleError(serverError!!)
        })
    }

    private fun handleError(serverError: ServerError<GitHubErrorBody>) {
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

    private fun showLoading() {
        layoutProgress.setVisible()
        layoutEmpty.setGone()
        layoutError.setGone()
        recyclerView.setGone()
    }

    private fun showEmpty() {
        layoutProgress.setGone()
        layoutEmpty.setVisible()
        layoutError.setGone()
        recyclerView.setGone()
    }

    private fun showContent(content: List<IssueResponse>) {
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
