package br.com.luisfernandez.github.client.repodetails.contributorslist

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
import br.com.luisfernandez.github.client.pojo.ContributorResponse
import br.com.luisfernandez.github.client.pojo.IssueResponse
import br.com.luisfernandez.github.client.repodetails.RepoDetailsPagerAdapter.Companion.OWNER
import br.com.luisfernandez.github.client.repodetails.RepoDetailsPagerAdapter.Companion.REPONAME
import kotlinx.android.synthetic.main.fragment_issues_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class ContributorsListFragment : Fragment(), ContributorsListView {

    lateinit var owner: String
    lateinit var repoName: String
    val viewModel by viewModel<ContributorsListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_contributors_list, container, false)

        val recyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(rootView.context)
        recyclerView.setHasFixedSize(true)

        setupArguments()

        setupViewModel()

        viewModel.loadContributorsList(owner, repoName)

        return rootView

    }

    private fun setupArguments() {
        owner = arguments!!.getString(OWNER)
        repoName = arguments!!.getString(REPONAME)
    }

    private fun setupViewModel() {
        viewModel.contributorsList.observe(this, Observer {
            issueList ->
            showContent(issueList!!)
        })

        viewModel.serverError.observe(this, Observer {
            serverError ->
            handleError(serverError!!)
        })
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
            viewModel.loadContributorsList(owner, repoName)
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

    override fun showContent(content: List<ContributorResponse>) {
        if (content.isEmpty()) {
            showEmpty()
        } else {
            layoutProgress.setGone()
            layoutEmpty.setGone()
            layoutError.setGone()
            recyclerView.setVisible()

            recyclerView.adapter = this.getContributorListAdapter(content)
        }
    }

    private fun getContributorListAdapter(content: List<ContributorResponse>): ContributorsListAdapter {
        return ContributorsListAdapter(
                content as ArrayList<ContributorResponse>
        )
    }
}
