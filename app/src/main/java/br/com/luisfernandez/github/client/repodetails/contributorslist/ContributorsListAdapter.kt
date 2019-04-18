package br.com.luisfernandez.github.client.repodetails.contributorslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.misc.ImageLoader
import br.com.luisfernandez.github.client.pojo.ContributorResponse
import kotlinx.android.synthetic.main.item_contributors_list.view.*
import java.util.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class ContributorsListAdapter(
        private val contributorsList: ArrayList<ContributorResponse> = ArrayList()
) : RecyclerView.Adapter<ContributorsListAdapter.ContributorViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contributors_list, parent, false)
        return ContributorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contributorsList.size
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val item = contributorsList[position]

        holder.textContributorName.text = item.login
        holder.textContributions.text = item.contributions

        ImageLoader.loadImage(item.avatar_url, holder.imageContributor)
    }

    class ContributorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageContributor = itemView.imageContributor!!
        var textContributorName = itemView.textContributorName!!
        var textContributions = itemView.textContributions!!
    }
}