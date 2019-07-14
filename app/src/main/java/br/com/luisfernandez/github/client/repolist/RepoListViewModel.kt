package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoListViewModel(
        private val repoListModel: RepoListModel,
        private val repoListRepository: RepoListRepository
) : ViewModel() {

    val listRepo = MutableLiveData<List<Repo>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadRepoList(page: Int, language: String) {

        repoListModel
                .loadRepoList(page, language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<List<Repo>, GitHubErrorBody>(GitHubErrorBody::class.java) {
                    override fun onError(error: ServerError<GitHubErrorBody>) {
                        serverError.postValue(error)
                    }

                    override fun onSuccess(repoList: List<Repo>) {
                        listRepo.postValue(repoList)
                    }
                })
    }

    suspend fun loadRepoListAsync(page: Int, language: String) {
        viewModelScope.launch {
            val repoList = repoListModel.loadRepoListCoroutineAsync(page, language)
            listRepo.postValue(repoList)
        }
    }

    fun loadRepoListAsyncCoroutine(page: Int, language: String) {
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