package br.com.luisfernandez.github.client

import br.com.luisfernandez.github.client.model.Repo
import rx.Observable


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListModel {
    fun loadRepoList(page: Int): Observable<List<Repo>>
}