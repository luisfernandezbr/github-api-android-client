package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pullrequest.PullRequestModel
import br.com.luisfernandez.github.client.pullrequest.PullRequestModelImpl
import br.com.luisfernandez.github.client.repolist.RepoListModel
import br.com.luisfernandez.github.client.repolist.RepoListModelImpl
import dagger.Module
import dagger.Provides

/**
 * Created by luisfernandez on 13/05/18.
 */
@Module
class ModelModule {

    @Provides
    fun providesRepoListModel(
            gitHubService: GitHubService
    ): RepoListModel = RepoListModelImpl(gitHubService)

    @Provides
    fun providesPullRequestModel(
            gitHubService: GitHubService
    ): PullRequestModel = PullRequestModelImpl(gitHubService)
}