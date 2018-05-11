package br.com.luisfernandez.github.client

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.luisfernandez.github.client.android.RepoListAdapter
import br.com.luisfernandez.github.client.model.Repo
import br.com.luisfernandez.github.client.model.RepoListResponse
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_home)
class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var presenter: HomePresenter

    @AfterViews
    fun afterViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        presenter = HomePresenterImpl()
        presenter.inject(this)
        presenter.loadRepoList()
    }

    override fun showRepoList(repoList: List<Repo>) {
        val recyclerView = recyclerView

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RepoListAdapter(repoList)
    }
}
