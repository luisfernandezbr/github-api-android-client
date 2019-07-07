package br.com.luisfernandez.github.client.repolist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import br.com.luisfernandez.github.client.dto.RepoDTO
import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.mvvm.DataWrapper
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListRepositoryImpl(
        private val gitHubService: GitHubService
) : RepoListRepository {

    val repoListLiveData = MutableLiveData<DataWrapper<List<RepoDTO>, String>>()
    
    override fun getRepoListLiveData(): LiveData<DataWrapper<List<RepoDTO>, String>> {
        return repoListLiveData
    }

    override fun loadRepoList(page: Int, language: String) {
        gitHubService.listReposLiveData(page, language).enqueue(object : Callback<RepoListResponse> {
            override fun onResponse(call: Call<RepoListResponse>, response: Response<RepoListResponse>) {
                var dataWrapper: DataWrapper<List<RepoDTO>, String>? = null

                if (response.isSuccessful) {
                    dataWrapper = DataWrapper(
                            statusCode = response.code(),
                            successData = response.body()?.repoDTOList
                    )
                }

                repoListLiveData.value = dataWrapper
            }

            override fun onFailure(call: Call<RepoListResponse>, t: Throwable) {
                repoListLiveData.value = DataWrapper(
                        statusCode = 1000,
                        errorData = ""
                )
            }
        })
    }
}