package br.com.luisfernandez.github.client.repolist.viewmodel

import androidx.lifecycle.*
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.dto.RepoDTO
import br.com.luisfernandez.github.client.mvp.BaseViewModel
import br.com.luisfernandez.github.client.repolist.model.RepoListModel
import br.com.luisfernandez.github.client.repolist.repository.RepoListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val repoListModel: RepoListModel,
        private val repoListRepository: RepoListRepository
) : BaseViewModel() {

    var listRepo = MediatorLiveData<List<RepoDTO>>()
    var serverError = MediatorLiveData<ServerError<GitHubErrorBody>>()

    fun loadRepoList(page: Int, language: String) {
        val searchData = SearchData(page, language)

        listRepo.addSource(repoListRepository.getRepoListLiveData()) { dataWrapper ->

            if (dataWrapper.successData != null) {
                listRepo.value = dataWrapper.successData
            } else {
                val error = ServerError<GitHubErrorBody>(
                        errorBody = null,
                        httpStatus = dataWrapper.statusCode,
                        errorMessage = dataWrapper.errorData
                )

                serverError.value = error
            }
        }

        repoListRepository.loadRepoList(page, language)
    }

    data class SearchData(
        val page: Int,
        val language: String
    )
}