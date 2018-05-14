package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.GitHubErrorBody
import br.com.luisfernandez.github.client.LoadContentView
import br.com.luisfernandez.github.client.model.Repo

/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListView : LoadContentView<List<Repo>, GitHubErrorBody>