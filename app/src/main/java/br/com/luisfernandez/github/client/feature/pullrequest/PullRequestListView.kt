package br.com.luisfernandez.github.client.feature.pullrequest

import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.pojo.PullRequestResponse
import br.com.luisfernandez.github.client.mvvm.view.LoadContentView


/**
 * Created by luisfernandez on 11/05/18.
 */
interface PullRequestListView : LoadContentView<List<PullRequestResponse>, GitHubErrorBody>