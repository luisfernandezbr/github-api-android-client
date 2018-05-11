package br.com.luisfernandez.github.client

/**
 * Created by luisfernandez on 11/05/18.
 */
interface HomePresenter {
    fun loadRepoList()
    fun inject(homeView: HomeView)
}