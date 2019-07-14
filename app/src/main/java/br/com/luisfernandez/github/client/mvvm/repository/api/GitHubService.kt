package br.com.luisfernandez.github.client.mvvm.repository.api

import br.com.luisfernandez.github.client.mvvm.repository.http.annotations.URL
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse
import br.com.luisfernandez.github.client.mvvm.repository.pojo.PullRequestResponse
import br.com.luisfernandez.github.client.mvvm.repository.pojo.RepoListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by luisfernandez on 10/05/18.
 */
@URL("https://api.github.com")
interface GitHubService {

    @GET("/search/repositories?sort=stars&per_page=10")
    fun listReposCoroutineAsync(@Query("page") page: Int, @Query("q") language: String): Deferred<Response<RepoListResponse>>

    @GET("/repos/{owner}/{repoName}/pulls")
    fun listPullRequestsAsync(@Path("owner") owner: String, @Path("repoName") repoName: String): Deferred<Response<List<PullRequestResponse>>>

    @GET("/repos/{owner}/{repoName}/issues")
    fun listIssuesAsync(@Path("owner") owner: String, @Path("repoName") repoName: String): Deferred<Response<List<IssueResponse>>>
}
