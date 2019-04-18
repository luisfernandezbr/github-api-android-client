package br.com.luisfernandez.github.client.repodetails.contributorslist

import br.com.luisfernandez.github.client.pojo.ContributorResponse
import io.reactivex.Observable

/**
 * Created by luisfernandez on 12/05/18.
 */
interface ContributorsListModel {
    fun loadContributorList(owner: String, repoName: String): Observable<List<ContributorResponse>>
}