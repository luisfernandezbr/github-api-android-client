package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListPresenter
import br.com.luisfernandez.github.client.repolist.RepoListPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by luisfernandez on 13/05/18.
 */
@Module
class PresenterModule {

    @Provides
    fun providesRepoListPresenter(repoListModel: RepoListModel
    ): RepoListPresenter = RepoListPresenterImpl(repoListModel)
}