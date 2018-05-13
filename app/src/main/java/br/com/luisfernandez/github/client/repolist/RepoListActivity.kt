package br.com.luisfernandez.github.client.repolist

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.OnItemClick
import br.com.luisfernandez.github.client.PaginationScrollListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.RepoListAdapter
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.ServerError
import br.com.luisfernandez.github.client.model.Repo
import br.com.luisfernandez.github.client.pullrequest.PullRequestListActivity_
import kotlinx.android.synthetic.main.activity_repo_list.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity



@SuppressLint("Registered")
@EActivity(R.layout.activity_repo_list)
class RepoListActivity : AppCompatActivity(), RepoListView {

    private var isLoadingState = false
    private var isLastPageState = false
    private var currentPage = 1
    private lateinit var repoListAdapter: RepoListAdapter

    private lateinit var presenter: RepoListPresenter

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
        presenter = RepoListPresenterImpl()
        presenter.inject(this)

        val recyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this)
        repoListAdapter = RepoListAdapter(onItemClick = object: OnItemClick<Repo> {
            override fun onItemClick(type: Repo) {
                PullRequestListActivity_
                        .intent(this@RepoListActivity)
                        .owner(type.owner.login)
                        .repoName(type.name)
                        .start()
            }
        })

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

        presenter.loadRepoList(1)
    }

    override fun showRepoList(repoList: List<Repo>) {
        repoListAdapter.addAll(repoList)

        val progressBar = progressBar
        progressBar.setGone()
        recyclerView.setVisible()

        isLoadingState = false
    }
}
