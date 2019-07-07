package br.com.luisfernandez.github.client.repolist.repository

import androidx.lifecycle.LiveData
import br.com.luisfernandez.github.client.dto.RepoDTO
import br.com.luisfernandez.github.client.mvvm.DataWrapper

interface RepoListRepository {
    fun loadRepoList(page: Int, language: String)
    fun getRepoListLiveData() : LiveData<DataWrapper<List<RepoDTO>, String>>
}