package br.com.luisfernandez.github.client.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.PullRequestResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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