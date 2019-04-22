package br.com.luisfernandez.github.client.repodetails.issuelist

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvp.LoadContentView
import br.com.luisfernandez.github.client.pojo.IssueResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface IssueListView : LoadContentView<List<IssueResponse>, GitHubErrorBody>