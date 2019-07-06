package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.livedata.*
import br.com.luisfernandez.github.client.pojo.Repo
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import io.reactivex.Observable

class RepoListLiveDataModelImpl (
        private val appExecutors: AppExecutors,
        private val gitHubService: GitHubService
) : RepoListLiveDataModel
{
    override fun loadRepoList(page: Int, language: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoListResponse>(appExecutors) {
            override fun processEmptyResponse(response: ApiEmptyResponse<RepoListResponse>): List<Repo> {
                return ArrayList()
            }

            override fun processResponse(response: ApiSuccessResponse<RepoListResponse>): List<Repo> {
                return response.body.repos
            }

            override fun createCall() = gitHubService.listReposLiveData(page, language)
        }.asLiveData()

    }
}