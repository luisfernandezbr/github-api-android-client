package br.com.luisfernandez.github.client.repodetails

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import br.com.luisfernandez.github.client.R
import kotlinx.android.synthetic.main.activity_repo_details.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra

@SuppressLint("Registered")
@EActivity(R.layout.activity_repo_details)
class RepoDetailsActivity : AppCompatActivity() {

    @Extra
    lateinit var owner: String

    @Extra
    lateinit var repoName: String

    @AfterViews
    fun afterViews() {
        this.configToolbar()

        val fragmentAdapter = RepoDetailsPagerAdapter(supportFragmentManager, owner, repoName)
            repo_detail_viewpager.adapter = fragmentAdapter

        repo_detail_tablayout.setupWithViewPager(repo_detail_viewpager)
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
}
