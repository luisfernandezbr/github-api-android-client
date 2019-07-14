package br.com.luisfernandez.github.client.http;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class CoroutineCallAdapter<T, SUCCESS, ERROR> implements CallAdapter<T, ResultWrapper<SUCCESS, ERROR>> {
    @Override
    public Type responseType() {
        return null;
    }

    @Override
    public ResultWrapper<SUCCESS, ERROR> adapt(Call<T> call) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

            }
        });

        return null;
    }
}
