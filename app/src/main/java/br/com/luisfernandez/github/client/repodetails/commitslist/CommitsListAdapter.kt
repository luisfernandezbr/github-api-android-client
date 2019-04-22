package br.com.luisfernandez.github.client.repodetails.commitslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.misc.ImageLoader
import br.com.luisfernandez.github.client.pojo.commit.CommitResponse
import kotlinx.android.synthetic.main.item_commits_list.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class CommitsListAdapter(
        private val commitsList: ArrayList<CommitResponse> = ArrayList()
) : RecyclerView.Adapter<CommitsListAdapter.CommitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_commits_list, parent, false)
        return CommitViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commitsList.size
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val item = commitsList[position]

        holder.textCommiterName.text = item.author.login
        holder.textCommitMessage.text = item.commit.message
        holder.textDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(item.commit.committer.date)

        ImageLoader.loadImage(item.author.avatarUrl, holder.imageCommiter)
    }

    class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageCommiter = itemView.imageCommitter!!
        var textCommiterName = itemView.textCommitterName!!
        var textCommitMessage = itemView.textCommitMessage!!
        var textDate = itemView.textDate!!
    }
}