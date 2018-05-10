package br.com.luisfernandez.github.client.android

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.model.Repo
import kotlinx.android.synthetic.main.item_repository_list.view.*

/**
 * Created by luisfernandez on 10/05/18.
 */
class RepoListAdapter(private val repoList: List<Repo>) :
        RecyclerView.Adapter<RepoListAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.repoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList[position]

        holder.textRepoName.text = repo.name
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRepoName = itemView.textRepoName
        val textRepoDescription = itemView.textRepoDescription
        val textRepoAuthor = itemView.textRepoAuthor
        val imageRepoAuthor = itemView.imageRepoAuthor
        val textStartCount = itemView.textStartCount
        val textForksCount = itemView.textForksCount
    }

}