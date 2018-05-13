package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.pullrequest.PullRequestListActivity
import br.com.luisfernandez.github.client.repolist.RepoListActivity
import br.com.luisfernandez.github.client.repolist.RepoListModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by luisfernandez on 13/05/18.
 */
@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        PresenterModule::class,
        ModelModule::class))
interface MainComponent {
    fun inject(repoListActivity: RepoListActivity)
    fun inject(pullRequestListActivity: PullRequestListActivity)
}