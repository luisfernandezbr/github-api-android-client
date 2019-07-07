package br.com.luisfernandez.github.client.mvvm

data class DataWrapper<SuccessData, ErrorData>(
    var statusCode: Int = -1,
    var keyValueMap: Map<String, String> = HashMap(),
    var successData: SuccessData? = null,
    var errorData: ErrorData?= null
)
