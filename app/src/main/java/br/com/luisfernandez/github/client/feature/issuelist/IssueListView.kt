package br.com.luisfernandez.github.client.feature.issuelist

import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.view.LoadContentView
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface IssueListView : LoadContentView<List<IssueResponse>, GitHubErrorBody>