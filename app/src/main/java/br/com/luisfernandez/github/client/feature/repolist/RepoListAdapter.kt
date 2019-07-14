package br.com.luisfernandez.github.client.feature.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.OnItemClickListener
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.android.ImageLoader
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo
import kotlinx.android.synthetic.main.endless_adapter_footer_error.view.*
import kotlinx.android.synthetic.main.item_repository_list.view.*

import kotlin.collections.ArrayList

/**
 * Created by luisfernandez on 10/05/18.
 */
class RepoListAdapter(
        private val onItemClickListener: OnItemClickListener<Repo>,
        private val onRetryClickListener: OnItemClickListener<String>,
        private val onIssueClickListener: OnItemClickListener<Repo>
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>()
{
    companion object {
        const val TAG = "RepoListAdapter"
        const val CONTENT = 1
        const val FOOTER = 2
        const val ERROR_FOOTER = 3
    }

    private val repoList: ArrayList<Repo> = ArrayList()

    private var isLoadingAdded = false
    private var isErrorAdded = false

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingAdded && position == this.repoList.size) {
            FOOTER
        } else if (isErrorAdded && position == this.repoList.size) {
            ERROR_FOOTER
        } else {
            CONTENT
        }
    }

    override fun getItemCount(): Int {
        return if (isLoadingAdded || isErrorAdded) this.repoList.size + 1 else this.repoList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when(viewType) {
            CONTENT -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository_list, parent, false))
            FOOTER -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading_more, parent, false))
            else -> ErrorFooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.endless_adapter_footer_error, parent, false))
        }
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        when (itemViewType) {
            CONTENT -> {
                val holderContent = viewHolder as ContentViewHolder

                val repo = getItem(position)
                holderContent.textRepoName.text = repo.name
                holderContent.textRepoDescription.text = repo.description
                holderContent.textRepoAuthor.text = repo.owner.login
                holderContent.textStartCount.text = repo.stargazersCount.toString()
                holderContent.textForksCount.text = repo.forksCount.toString()
                holderContent.textIssuesCount.text = repo.openIssuesCount.toString()

                ImageLoader.loadImage(repo.owner.avatarUrl, holderContent.imageRepoAuthor)

                viewHolder.itemView.setOnClickListener { _ ->
                    onItemClickListener.onItemClick(repo)
                }

                viewHolder.textIssuesCount.setOnClickListener { _ ->
                    onIssueClickListener.onItemClick(repo)
                }
            }
            ERROR_FOOTER -> {
                val errorFooterHolder = viewHolder as ErrorFooterViewHolder
                errorFooterHolder.itemView.setOnClickListener {
                    onRetryClickListener.onItemClick("")
                }

                errorFooterHolder.buttonRetry.setOnClickListener {
                    onRetryClickListener.onItemClick("")
                }
            }
        }
    }

    private fun getItem(position: Int): Repo {
        return this.repoList[position]
    }

    fun showFooter() {
        isLoadingAdded = true
        isErrorAdded = false
        notifyItemInserted(itemCount)
    }

    fun showErrorFooter() {
        isLoadingAdded = false
        isErrorAdded = true
        notifyItemInserted(itemCount)
    }

    fun addAll(repoList: List<Repo>) {
        isLoadingAdded = false
        notifyItemInserted(itemCount)

        this.repoList.addAll(repoList)
        isLoadingAdded = false
        notifyDataSetChanged()
    }

    class ContentViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val textRepoName = itemView.textRepoName!!
        val textRepoDescription = itemView.textRepoDescription!!
        val textRepoAuthor = itemView.textRepoAuthor!!
        val imageRepoAuthor = itemView.imageRepoAuthor!!
        val textStartCount = itemView.textStartCount!!
        val textForksCount = itemView.textForksCount!!
        val textIssuesCount = itemView.textIssuesCount!!
    }

    class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    class ErrorFooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val buttonRetry = itemView.buttonRetry!!
    }

    fun clear() {
        repoList.clear()
    }
}