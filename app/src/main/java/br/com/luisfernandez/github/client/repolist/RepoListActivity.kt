package br.com.luisfernandez.github.client.repolist

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import br.com.luisfernandez.github.client.OnItemClickListener
import br.com.luisfernandez.github.client.PaginationScrollListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.extensions.setGone
import br.com.luisfernandez.github.client.extensions.setVisible
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pullrequest.PullRequestListActivity_
import br.com.luisfernandez.github.client.repodetails.RepoDetailsActivity_
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.SearchEvent
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_state_empty.*
import kotlinx.android.synthetic.main.view_state_error.*
import kotlinx.android.synthetic.main.view_state_loading.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


@SuppressLint("Registered")
@EActivity(R.layout.activity_list)
class RepoListActivity : AppCompatActivity(), RepoListView {

    private var isLoadingState = false
    private var isLastPageState = false
    private var currentPage = 1

    private var querySearch = "Java"
    private lateinit var repoListAdapter: RepoListAdapter

    private val viewModel by viewModel<RepoListViewModel>()

    @AfterViews
    fun afterViews() {
        this.configToolbar()

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                querySearch = query
                setToolbarTitle(querySearch)
                viewModel.loadRepoList(currentPage, query)
                repoListAdapter.clear()
                sendQueryEvent(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })

        this.initRecyclerView()

        setupViewModel()

        viewModel.loadRepoList(currentPage, querySearch)

        sendQueryEvent(querySearch)
    }

    private fun setupViewModel() {
        viewModel.listRepo.observe(this, Observer {
            listRepo ->
            showContent(listRepo!!)
        })

        viewModel.serverError.observe(this, Observer {
            serverError ->
            handleError(serverError!!)
        })
    }

    private fun sendQueryEvent(value: String) {
        Answers.getInstance().logSearch(SearchEvent().putQuery(value))
    }

    private fun configToolbar() {
        this.setSupportActionBar(toolbar)
        this.setToolbarTitle(querySearch)
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = "language: $title"
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        repoListAdapter = this.getRepoListAdapter()
        recyclerView.adapter = repoListAdapter

        val scrollListener = this.getPaginationScrollListener(layoutManager)
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun getRepoListAdapter(): RepoListAdapter {
        return RepoListAdapter(
                getOnItemClickListener(),
                getOnLoadMoreContentClickListener()
        )
    }

    private fun getOnItemClickListener(): OnItemClickListener<Repo> {
        return object : OnItemClickListener<Repo> {
            override fun onItemClick(type: Repo) {
                goToRepoDetailsActivity(type)
            }
        }
    }

    private fun getOnLoadMoreContentClickListener(): OnItemClickListener<String> {
        return object : OnItemClickListener<String> {
            override fun onItemClick(type: String) {
                loadMoreContent()
            }
        }
    }

    private fun goToRepoDetailsActivity(type: Repo) {
        RepoDetailsActivity_
                .intent(this@RepoListActivity)
                .owner(type.owner.login)
                .repoName(type.name)
                .start()
    }

    private fun loadMoreContent() {
        this@RepoListActivity.isLoadingState = true
        recyclerView.post { repoListAdapter.showFooter() }
        currentPage += 1

        viewModel.loadRepoList(currentPage, querySearch)
        sendQueryEvent(querySearch)
    }

    override fun handleError(serverError: ServerError<GitHubErrorBody>) {
        if (currentPage == 1) {
            this.showErrorState()

            when(serverError.httpStatus) {
                422 -> {
                    textErrorMessage.text = String.format(getString(R.string.error_message_invalid_language), querySearch)
                    buttonRetry.setGone()
                }
                else -> {
                    if (serverError.errorBody != null) {
                        textErrorMessage.text = serverError.errorBody!!.message
                    } else {
                        textErrorMessage.text = serverError.errorMessage
                    }
                }
            }

            buttonRetry.setOnClickListener { _ ->
                showLoading()
                viewModel.loadRepoList(currentPage, querySearch)
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

    private fun showErrorState() {
        layoutProgress.setGone()
        layoutEmpty.setGone()
        layoutError.setVisible()
        recyclerView.setGone()
    }

    private fun getPaginationScrollListener(layoutManager: LinearLayoutManager): PaginationScrollListener {
        return object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                loadMoreContent()
            }

            override fun isLastPage(): Boolean {
                return isLastPageState
            }

            override fun isLoading(): Boolean {
                return isLoadingState
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)

        return true
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }
}
