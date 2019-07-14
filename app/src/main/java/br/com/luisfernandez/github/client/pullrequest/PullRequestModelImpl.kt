package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import io.reactivex.Observable

class PullRequestModelImpl (
    private val gitHubService: GitHubService
) : PullRequestModel
{

}