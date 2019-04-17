package br.com.luisfernandez.github.client.repodetails

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import br.com.luisfernandez.github.client.R

class RepoDetailsPagerAdapter(private val context: Context, fm: FragmentManager, owner: String, repoName: String):
    FragmentPagerAdapter(fm) {

    val owner = owner
    val repoName = repoName

    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("OWNER", owner)
        bundle.putString("REPONAME", repoName)

        var fragment = when(position) {
            0 -> PullRequestsFragment()
            1 -> IssuesListFragment()
            2 -> CommitsFragment()
            3 -> CollaboratorsFragment()
            4 -> BranchesFragment()

            else -> PullRequestsFragment()
        }

        fragment.arguments = bundle

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.getString(R.string.titlePullRequestsTab)
            1 -> context.getString(R.string.titleIssuesTab)
            2 -> context.getString(R.string.titleCommitsTab)
            3 -> context.getString(R.string.titleCollaboratorsTab)
            4 -> context.getString(R.string.titleBranchesTab)

            else -> context.getString(R.string.titlePullRequestsTab)
        }
    }

}