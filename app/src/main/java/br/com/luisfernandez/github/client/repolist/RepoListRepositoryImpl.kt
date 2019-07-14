package br.com.luisfernandez.github.client.repolist

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.ResultWrapper
import br.com.luisfernandez.github.client.pojo.RepoListResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepoListRepositoryImpl(
        private val gitHubService: GitHubService
) : RepoListRepository {
    override suspend fun loadRepoListCoroutineAsync(page: Int, language: String): ResultWrapper<RepoListResponse, String> {
        return withContext(Dispatchers.IO) {
            val deferredResponse = gitHubService.listReposCoroutine(page, language)
            DeferredResponseHandler<RepoListResponse, String>(deferredResponse).handle()
        }
    }
}

class DeferredResponseHandler<S, E>(
        var deferredResponse: Deferred<Response<S>>
) {
    suspend fun handle(): ResultWrapper<S, E>{
        val resultWrapper = ResultWrapper<S, E>()

        try {
            val response = deferredResponse.await()

            if (response.isSuccessful) {
                resultWrapper.success = if (response.body() != null) {
                    @Suppress("UNCHECKED_CAST")
                    response.body() as S
                } else {
                    null
                }
            } else {
                //resultWrapper.error = response.errorBody().toString()
            }

            resultWrapper.statusCode = response.code()

            val headers = response.headers()
            headers.names().map { headerKey ->
                val headerValue = headers.get(headerKey)
                resultWrapper.addKeyValue(headerKey , headerValue ?: "")
            }


        } catch(e: Exception) {
            resultWrapper.error = e.message as E
        }

        return resultWrapper
    }

}