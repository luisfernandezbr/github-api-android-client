package br.com.luisfernandez.github.client.repodetails.commitslist

import br.com.luisfernandez.github.client.pojo.CommitResponse
import br.com.luisfernandez.github.client.pojo.ContributorResponse
import io.reactivex.Observable

/**
 * Created by luisfernandez on 12/05/18.
 */
interface CommitsListModel {
    fun loadCommitsList(owner: String, repoName: String): Observable<List<CommitResponse>>
}