package br.com.luisfernandez.github.client.http;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ResultWrapper<SUCCESS, ERROR> {

    private SUCCESS success;
    private ERROR error;
    private Map<String, String> keyValueMap;
    private int statusCode;

    public SUCCESS getSuccess() {
        return success;
    }

    public void setSuccess(SUCCESS success) {
        this.success = success;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }

    public void addKeyValue(String key, String value ) {
        if (this.keyValueMap == null) {
            this.keyValueMap = new HashMap<>();
        }

        this.keyValueMap.put(key, value);
    }

    @Nullable
    public String getValue(String key) {
        String result = null;

        if (keyValueMap != null) {
            result = keyValueMap.get(key);
        }

        return result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
