package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.pojo.IssueResponse
import io.reactivex.Observable

/**
 * Created by luisfernandez on 12/05/18.
 */
interface IssueListModel {
    fun loadIssueList(owner: String, repoName: String): Observable<List<IssueResponse>>
}