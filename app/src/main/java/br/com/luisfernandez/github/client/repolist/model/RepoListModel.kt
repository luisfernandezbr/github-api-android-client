package br.com.luisfernandez.github.client.repolist.model

import br.com.luisfernandez.github.client.dto.RepoDTO
import io.reactivex.Observable


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListModel {
    fun loadRepoList(page: Int, language: String): Observable<List<RepoDTO>>
}