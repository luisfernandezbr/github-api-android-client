package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import br.com.luisfernandez.github.client.pojo.User
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListModel {
    fun loadRepoList(page: Int, language: String): Observable<List<Repo>>

    suspend fun loadRepoListCoroutineAsync(page: Int, language: String): List<Repo>

    suspend fun loadRepoListCoroutine(page: Int, language: String): ResultWrapper<List<Repo>, GitHubErrorBody>
}