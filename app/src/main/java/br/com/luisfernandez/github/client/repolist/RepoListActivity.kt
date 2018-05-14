package br.com.luisfernandez.github.client.repolist

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.OnItemClick
import br.com.luisfernandez.github.client.PaginationScrollListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.Repo
import br.com.luisfernandez.github.client.pullrequest.PullRequestListActivity_
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import javax.inject.Inject

@SuppressLint("Registered")
@EActivity(R.layout.activity_repo_list)
class RepoListActivity : AppCompatActivity(), RepoListView {

    init {
        AppApplication.component.inject(this)
    }

    private var isLoadingState = false
    private var isLastPageState = false
    private var currentPage = 1
    private lateinit var repoListAdapter: RepoListAdapter

    @Inject
    lateinit var presenter: RepoListPresenter

    @AfterViews
    fun afterViews() {
        presenter.inject(this)

        val layoutManager = LinearLayoutManager(this)
        repoListAdapter = RepoListAdapter(onItemClick = object : OnItemClick<Repo> {
            override fun onItemClick(type: Repo) {
                PullRequestListActivity_
                        .intent(this@RepoListActivity)
                        .owner(type.owner.login)
                        .repoName(type.name)
                        .start()
            }
        })

        val recyclerView = recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = repoListAdapter
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                this@RepoListActivity.isLoadingState = true
                recyclerView.post { repoListAdapter.showFooter()}
                currentPage += 1

                presenter.loadRepoList(currentPage)
            }

            override fun isLastPage(): Boolean {
                return isLastPageState
            }

            override fun isLoading(): Boolean {
                return isLoadingState
            }
        })

        presenter.loadRepoList(currentPage)
    }

    override fun handleError(serverError: ServerError<GitHubErrorBody>) {
        layoutProgress.setGone()
        layoutEmpty.setGone()
        layoutError.setVisible()
        recyclerView.setGone()

        textErrorMessage.text = serverError.errorBody!!.message

        buttonRetry.setOnClickListener { _ ->
            showLoading()
            presenter.loadRepoList(currentPage)
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

    override fun showContent(content: List<Repo>) {
        repoListAdapter.addAll(content)

        if (content.isEmpty()) {

            if (currentPage > 1) {
                currentPage--
            } else {
                showEmpty()
            }

        } else {
            layoutProgress.setGone()
            layoutEmpty.setGone()
            layoutError.setGone()
            recyclerView.setVisible()

            isLoadingState = false
        }
    }
}
