package br.com.luisfernandez.github.client.pullrequest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.misc.ImageLoader
import br.com.luisfernandez.github.client.model.PullRequestResponse
import kotlinx.android.synthetic.main.item_pull_request_list.view.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class PullRequestListAdapter(
        private val pullRequestList: ArrayList<PullRequestResponse> = ArrayList()
) : RecyclerView.Adapter<PullRequestListAdapter.PullRequestViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pull_request_list, parent, false)
        return PullRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val item = pullRequestList[position]

        holder.textTitle.text = item.title
        holder.textAuthorName.text = item.user.login
        holder.textDate.text = item.createdAt
        holder.textDescription.text = item.body

        ImageLoader.loadImage(item.user.avatarUrl, holder.imagePullRequestAuthor)
    }

    class PullRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagePullRequestAuthor = itemView.imagePullRequestAuthor!!
        var textAuthorName = itemView.textAuthorName!!
        var textTitle = itemView.textTitle!!
        var textDate = itemView.textDate!!
        var textDescription = itemView.textDescription!!
    }
}