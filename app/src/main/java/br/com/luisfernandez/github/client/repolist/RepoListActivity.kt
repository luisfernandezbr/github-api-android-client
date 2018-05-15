package br.com.luisfernandez.github.client.repolist

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.luisfernandez.github.client.OnItemClickListener
import br.com.luisfernandez.github.client.PaginationScrollListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.GitHubErrorBody
import br.com.luisfernandez.github.client.model.Repo
import br.com.luisfernandez.github.client.pullrequest.PullRequestListActivity_
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import javax.inject.Inject

@SuppressLint("Registered")
@EActivity(R.layout.activity_list)
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
        repoListAdapter = RepoListAdapter(
                getOnItemClickListener(),
                getOnLoadMoreContentClickListener()
        )

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = repoListAdapter
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                loadMoreContent()
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
        if (currentPage == 1) {
            layoutProgress.setGone()
            layoutEmpty.setGone()
            layoutError.setVisible()
            recyclerView.setGone()


            if (serverError.errorBody != null) {
                textErrorMessage.text = serverError.errorBody!!.message
            } else {
                textErrorMessage.text = serverError.errorMessage
            }

            buttonRetry.setOnClickListener { _ ->
                showLoading()
                presenter.loadRepoList(currentPage)
            }
        } else {
            repoListAdapter.showErrorFooter()
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

    private fun getOnLoadMoreContentClickListener(): OnItemClickListener<String> {
        return object : OnItemClickListener<String> {
            override fun onItemClick(type: String) {
                loadMoreContent()
            }
        }
    }

    private fun loadMoreContent() {
        this@RepoListActivity.isLoadingState = true
        recyclerView.post { repoListAdapter.showFooter() }
        currentPage += 1

        presenter.loadRepoList(currentPage)
    }

    private fun getOnItemClickListener(): OnItemClickListener<Repo> {
        return object : OnItemClickListener<Repo> {
            override fun onItemClick(type: Repo) {
                PullRequestListActivity_
                        .intent(this@RepoListActivity)
                        .owner(type.owner.login)
                        .repoName(type.name)
                        .start()
            }
        }
    }
}
