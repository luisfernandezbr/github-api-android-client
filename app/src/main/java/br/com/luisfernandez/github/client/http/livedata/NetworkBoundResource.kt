/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.luisfernandez.github.client.http.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <ApiReturnType>
</ApiReturnType></ResultType> */
abstract class NetworkBoundResource<SuccessType, ApiReturnType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<SuccessType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<SuccessType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val liveDataApiResponse = createCall()

        result.addSource(liveDataApiResponse) { apiResponse ->
            result.removeSource(liveDataApiResponse)

            when (apiResponse) {
                is ApiSuccessResponse -> {
                    val responseBody = processResponse(apiResponse)

                    appExecutors.mainThread().execute {
                        setValue(Resource.success(responseBody))
                    }

                }
                is ApiEmptyResponse -> {
                    val responseBody = processEmptyResponse(apiResponse)

                    appExecutors.mainThread().execute {
                        setValue(Resource.success(responseBody ))
                    }
                }
                is ApiErrorResponse -> {
                    // TODO Call to parse error here?
                    onFetchFailed()
                    setValue(Resource.error(apiResponse.errorMessage, null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<SuccessType>>

    @WorkerThread
    protected abstract fun processResponse(response: ApiSuccessResponse<ApiReturnType>) : SuccessType

    @WorkerThread
    protected abstract fun processEmptyResponse(response: ApiEmptyResponse<ApiReturnType>) : SuccessType

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<ApiReturnType>>
}
