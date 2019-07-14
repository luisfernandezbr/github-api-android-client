package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.view.LoadContentView
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo

/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListView : LoadContentView<List<Repo>, GitHubErrorBody>