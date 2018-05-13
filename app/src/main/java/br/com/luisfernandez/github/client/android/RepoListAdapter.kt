package br.com.luisfernandez.github.client.android

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luisfernandez.github.client.OnItemClick
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.misc.ImageLoader
import br.com.luisfernandez.github.client.model.Repo
import kotlinx.android.synthetic.main.item_loading_more.view.*
import kotlinx.android.synthetic.main.item_repository_list.view.*

import kotlin.collections.ArrayList

/**
 * Created by luisfernandez on 10/05/18.
 */
class RepoListAdapter(
        private val repoList: ArrayList<Repo> = ArrayList(),
        val onItemClick: OnItemClick<Repo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    companion object {
        const val TAG = "RepoListAdapter"
        const val CONTENT = 1
        const val FOOTER = 2
    }

    private var isLoadingAdded = false

    override fun getItemViewType(position: Int): Int {
        if (isLoadingAdded && position == this.repoList.size) {
            Log.d(TAG, "getItemViewType(): $FOOTER")
            return FOOTER
        } else {
            Log.d(TAG, "getItemViewType(): $CONTENT")
            return CONTENT
        }
    }

    override fun getItemCount(): Int {
        return if (isLoadingAdded) this.repoList.size + 1 else this.repoList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder() itemType: $viewType")

        return when(viewType) {
            CONTENT -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository_list, parent, false))
            else -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading_more, parent, false))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        when (itemViewType) {
            CONTENT -> {
                val holderContent = viewHolder as ContentViewHolder

                Log.d(TAG, "listSize: ${repoList.size}")

                val repo = getItem(position)
                holderContent.textRepoName.text = repo.name
                holderContent.textRepoDescription.text = repo.description
                holderContent.textRepoAuthor.text = repo.owner.login
                holderContent.textStartCount.text = repo.stargazersCount.toString()
                holderContent.textForksCount.text = repo.forksCount.toString()

                ImageLoader.loadImage(repo.owner.avatarUrl, holderContent.imageRepoAuthor)

                viewHolder.itemView.setOnClickListener { view ->
                    onItemClick.onItemClick(repo)
                    Toast.makeText(view.context, "asdasdasd", Toast.LENGTH_LONG).show()
                }
            }
//            else -> {
//                val holderContent = viewHolder as FooterViewHolder
//            }
        }
    }

    private fun getItem(position: Int): Repo {
        return this.repoList[position]
    }

    fun showFooter() {
        Log.d(TAG, "showFooter()")
        isLoadingAdded = true
        notifyItemInserted(itemCount)
    }

    fun addAll(repoList: List<Repo>) {
        isLoadingAdded = false
        notifyItemInserted(itemCount)

        this.repoList.addAll(repoList)
        isLoadingAdded = false
        notifyDataSetChanged()
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRepoName = itemView.textRepoName!!
        val textRepoDescription = itemView.textRepoDescription!!
        val textRepoAuthor = itemView.textRepoAuthor!!
        val imageRepoAuthor = itemView.imageRepoAuthor!!
        val textStartCount = itemView.textStartCount!!
        val textForksCount = itemView.textForksCount!!
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView.progressBar!!

    }
}