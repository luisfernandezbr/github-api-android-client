package br.com.luisfernandez.github.client.repodetails.commitslist

import br.com.luisfernandez.github.client.pojo.commit.CommitResponse
import io.reactivex.Observable

/**
 * Created by luisfernandez on 12/05/18.
 */
interface CommitsListModel {
    fun loadCommitsList(owner: String, repoName: String): Observable<List<CommitResponse>>
}