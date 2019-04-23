package br.com.luisfernandez.github.client.repodetails.brancheslist

import br.com.luisfernandez.github.client.pojo.BranchResponse
import io.reactivex.Observable

/**
 * Created by luisfernandez on 12/05/18.
 */
interface BranchesListModel {
    fun loadBranchesList(owner: String, repoName: String): Observable<List<BranchResponse>>
}