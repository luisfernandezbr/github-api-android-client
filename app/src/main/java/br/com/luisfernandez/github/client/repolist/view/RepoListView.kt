package br.com.luisfernandez.github.client.repolist.view

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvp.LoadContentView
import br.com.luisfernandez.github.client.dto.RepoDTO

/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListView : LoadContentView<List<RepoDTO>, GitHubErrorBody>