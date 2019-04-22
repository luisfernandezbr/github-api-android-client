package br.com.luisfernandez.github.client.repodetails.commitslist

import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.mvp.LoadContentView
import br.com.luisfernandez.github.client.pojo.commit.CommitResponse


/**
 * Created by luisfernandez on 11/05/18.
 */
interface CommitsListView : LoadContentView<List<CommitResponse>, GitHubErrorBody>