package br.com.luisfernandez.github.client.repodetails.brancheslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.luisfernandez.github.client.R
import br.com.luisfernandez.github.client.pojo.BranchResponse
import kotlinx.android.synthetic.main.item_branches_list.view.*
import java.util.*

/**
 * Created by luisfernandez on 12/05/18.
 */
class BranchesListAdapter(
        private val branchesList: ArrayList<BranchResponse> = ArrayList()
) : RecyclerView.Adapter<BranchesListAdapter.BranchViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_branches_list, parent, false)
        return BranchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return branchesList.size
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val item = branchesList[position]

        holder.textBranchName.text = item.name

    }

    class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textBranchName = itemView.textBranchName!!
    }
}