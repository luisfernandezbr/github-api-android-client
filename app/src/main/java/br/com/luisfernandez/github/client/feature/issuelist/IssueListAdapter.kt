package br.com.luisfernandez.github.client.feature.issuelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.ImageLoader
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse
import kotlinx.android.synthetic.main.item_issue_list.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class IssueListAdapter(
        private val issueList: ArrayList<IssueResponse> = ArrayList()
) : androidx.recyclerview.widget.RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue_list, parent, false)
        return IssueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issueList.size
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = issueList[position]



        holder.textTitle.text = item.title
        holder.textAuthorName.text = item.user.login
        holder.textDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(item.createdAt)
        holder.textDescription.text = item.body

        ImageLoader.loadImage(item.user.avatarUrl, holder.imageIssueAuthor)
    }

    class IssueViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var imageIssueAuthor = itemView.imageIssueAuthor!!
        var textAuthorName = itemView.textIssueAuthorName!!
        var textTitle = itemView.textIssueTitle!!
        var textDate = itemView.textIssueDate!!
        var textDescription = itemView.textIssueDescription!!
    }
}