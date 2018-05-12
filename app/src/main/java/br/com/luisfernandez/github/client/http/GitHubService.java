package br.com.luisfernandez.github.client.http;

import br.com.luisfernandez.github.client.http.annotations.URL;
import br.com.luisfernandez.github.client.model.RepoListResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by luisfernandez on 10/05/18.
 */

@URL("https://api.github.com")
public interface GitHubService
{
    @GET("/search/repositories?q=language:Java&sort=stars&per_page=10")
    Observable<RepoListResponse> listRepos(@Query("page") int page);
}
