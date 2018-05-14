package br.com.luisfernandez.github.client.pullrequest

import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.LoadContentView
import br.com.luisfernandez.github.client.model.PullRequestResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface PullRequestListView : LoadContentView<List<PullRequestResponse>, GitHubErrorBody>