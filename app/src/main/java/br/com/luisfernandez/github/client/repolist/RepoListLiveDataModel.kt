package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.LiveData
import br.com.luisfernandez.github.client.http.livedata.Resource
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.Observable


/**
 * Created by luisfernandez on 11/05/18.
 */
interface RepoListLiveDataModel {
    fun loadRepoList(page: Int, language: String): LiveData<Resource<List<Repo>>>
}