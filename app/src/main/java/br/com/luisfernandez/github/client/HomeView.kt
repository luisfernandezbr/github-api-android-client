package br.com.luisfernandez.github.client

import br.com.luisfernandez.github.client.model.Repo

/**
 * Created by luisfernandez on 11/05/18.
 */
interface HomeView {
    fun showRepoList(repoList: List<Repo>)
}