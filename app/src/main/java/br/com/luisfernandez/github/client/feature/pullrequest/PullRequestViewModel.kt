package br.com.luisfernandez.github.client.feature.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.GitHubErrorBody
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.ServerError
import br.com.luisfernandez.github.client.mvvm.repository.pojo.PullRequestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PullRequestViewModel (
    private val pullRequestRepository: PullRequestRepository
) : ViewModel()
{

    val listPullRequest = MutableLiveData<List<PullRequestResponse>>()
    val serverError = MutableLiveData<ServerError<GitHubErrorBody>>()

    fun loadPullRequestList(owner: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultWrapper = pullRequestRepository.loadPullRequestList(owner, repoName)

            if (resultWrapper.success != null) {
              listPullRequest.postValue(resultWrapper.success)
            }
        }
    }
}