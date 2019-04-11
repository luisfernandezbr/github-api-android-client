package br.com.luisfernandez.github.client.repodetails

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class RepoDetailsPagerAdapter(fm: FragmentManager):
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> PullRequestsFragment()
            1 -> IssuesFragment()
            2 -> CommitsFragment()
            3 -> CollaboratorsFragment()
            4 -> BranchesFragment()

            else -> PullRequestsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "PullRequests"
            1 -> "Issues"
            2 -> "Commits"
            3 -> "Collaborators"
            4 -> "Branches"

            else -> "PullRequests"
        }
    }

}