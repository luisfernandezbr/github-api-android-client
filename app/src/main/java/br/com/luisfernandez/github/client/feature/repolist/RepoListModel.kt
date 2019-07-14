package br.com.luisfernandez.github.client.feature.repolist

import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.Repo


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListModel {

    suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody>
}