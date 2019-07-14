package br.com.luisfernandez.github.client.mvvm.viewresource

class TestViewResource {

    fun test() {
        val success = DataResource(
            Status.SUCCESS,
                DataWrapper<TestUser, HttpError>(
                        TestUser("Luis")
                )
        )
        handleResult(success)

        val error = DataResource(
                Status.ERROR,
                DataWrapper<TestUser, HttpError>(
                        error = HttpError("")
                )
        )
        handleResult(error)


        val loading = DataResource.loading<DataWrapper<TestUser, HttpError>>()
        handleResult(loading)
    }

    private fun handleResult(dataResource: DataResource<DataWrapper<TestUser, HttpError>>) {
        when(dataResource.status) {
            Status.SUCCESS -> {
                val success = dataResource.payloadData?.success
            }

            Status.ERROR -> {
                val success = dataResource.payloadData?.error
            }

            Status.LOADING -> {
                // loading
            }

        }
    }

    fun testResult(viewResource: ViewResource<Payload>) {
        when (viewResource) {
            is ViewResourceLoading<Payload> -> {
                viewResource.payload
            }
        }
    }
}

data class DataWrapper<out Success, out Error>(
    val success: Success? = null,
    val error: Error? = null
)

data class TestUser(
        var name: String
)

data class HttpError(
        var name: String
)