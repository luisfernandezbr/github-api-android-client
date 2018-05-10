package br.com.luisfernandez.github.client.http;

import br.com.luisfernandez.github.client.model.RepoListResponse;
import br.com.luisfernandez.github.client.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by luisfernandez on 10/05/18.
 */

public interface GitHubService
{
    @GET("/search/repositories?q=language:Java&sort=stars&page=1")
    Call<RepoListResponse> listRepos();
}
