package br.com.luisfernandez.github.client.repodetails

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import br.com.luisfernandez.github.client.R
import kotlinx.android.synthetic.main.activity_list.*
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
