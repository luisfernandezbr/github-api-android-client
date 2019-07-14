package br.com.luisfernandez.github.client.feature.issuelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.ServerError
import br.com.luisfernandez.github.client.mvvm.repository.pojo.IssueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueListViewModel(
        private val issueListRepository: IssueListRepository
) : ViewModel() {

    val issueList = MutableLiveData<List<IssueResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadIssueList(owner: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultWrapper = issueListRepository.loadIssueList(owner, repoName)

            if (resultWrapper.success != null) {
                issueList.postValue(resultWrapper.success)
            }
        }
    }
}