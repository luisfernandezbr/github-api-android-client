package br.com.luisfernandez.github.client.pullrequest

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.OnItemClickListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.misc.ImageLoader
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import kotlinx.android.synthetic.main.item_pull_request_list.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class PullRequestListAdapter(
        private val pullRequestList: ArrayList<PullRequestResponse> = ArrayList(),
        private val onItemClickListener: OnItemClickListener<PullRequestResponse>
) : androidx.recyclerview.widget.RecyclerView.Adapter<PullRequestListAdapter.PullRequestViewHolder>()
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
        holder.textDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(item.createdAt)
        holder.textDescription.text = item.body

        ImageLoader.loadImage(item.user.avatarUrl, holder.imagePullRequestAuthor)

        holder.itemView.setOnClickListener { _ ->
            onItemClickListener.onItemClick(item)
        }
    }

    class PullRequestViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var imagePullRequestAuthor = itemView.imagePullRequestAuthor!!
        var textAuthorName = itemView.textAuthorName!!
        var textTitle = itemView.textTitle!!
        var textDate = itemView.textDate!!
        var textDescription = itemView.textDescription!!
    }
}