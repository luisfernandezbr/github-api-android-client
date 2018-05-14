package br.com.luisfernandez.github.client.http;

import java.util.List;

import br.com.luisfernandez.github.client.http.annotations.URL;
import br.com.luisfernandez.github.client.model.PullRequestResponse;
import br.com.luisfernandez.github.client.model.RepoListResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by luisfernandez on 10/05/18.
 */
@URL("https://api.github.com")
public interface GitHubService
{
    @GET("/search/repositories?q=user:square&sort=stars&per_page=6")
    Observable<RepoListResponse> listRepos(@Query("page") int page);

    @GET("https://api.github.com/repos/{owner}/{repoName}/pulls")
    Observable<List<PullRequestResponse>> listPullRequests(@Path("owner") String owner, @Path("repoName") String repoName);
}
