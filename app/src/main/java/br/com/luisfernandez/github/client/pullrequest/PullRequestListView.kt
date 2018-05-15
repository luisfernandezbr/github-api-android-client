package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvp.LoadContentView
import br.com.luisfernandez.github.client.pojo.PullRequestResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface PullRequestListView : LoadContentView<List<PullRequestResponse>, GitHubErrorBody>