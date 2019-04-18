package br.com.luisfernandez.github.client.repodetails.contributorslist

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvp.LoadContentView
import br.com.luisfernandez.github.client.pojo.ContributorResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface ContributorsListView : LoadContentView<List<ContributorResponse>, GitHubErrorBody>