package br.com.luisfernandez.github.client.http;

import br.com.luisfernandez.github.client.http.annotations.URL;
import br.com.luisfernandez.github.client.model.RepoListResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by luisfernandez on 10/05/18.
 */

@URL("https://api.github.com")
public interface GitHubService
{
    @GET("/search/repositories?q=language:Java&sort=stars&page=1")
    Observable<RepoListResponse> listRepos();
}
