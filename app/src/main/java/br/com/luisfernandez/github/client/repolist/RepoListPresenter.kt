package br.com.luisfernandez.github.client.repolist

/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListPresenter {
    fun loadRepoList(page: Int, language: String)
    fun inject(repoListView: RepoListView)
}