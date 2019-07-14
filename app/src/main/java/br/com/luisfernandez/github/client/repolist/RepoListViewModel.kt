package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoListViewModel(
        private val repoListModel: RepoListModel
) : ViewModel() {

    val listRepo = MutableLiveData<List<Repo>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadRepoList(page: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultWrapper = repoListModel.loadRepoListCoroutine(page, language)

            if (resultWrapper.success != null) {
                listRepo.postValue(resultWrapper.success)
            } else {
                serverError.postValue(ServerError(
                        httpStatus = resultWrapper.statusCode,
                        errorBody = resultWrapper.error,
                        errorMessage = resultWrapper.genericErrorMessage

                ))
            }
        }
    }
}