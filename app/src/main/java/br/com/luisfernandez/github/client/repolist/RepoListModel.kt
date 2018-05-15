package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.Observable


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListModel {
    fun loadRepoList(page: Int): Observable<List<Repo>>
}