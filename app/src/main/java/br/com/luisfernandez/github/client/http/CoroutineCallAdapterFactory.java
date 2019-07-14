package br.com.luisfernandez.github.client.http;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kotlinx.coroutines.Deferred;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CoroutineCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        boolean isDeferredInstance = returnType instanceof Deferred;

        if (!isDeferredInstance) {
            return null;
        }

        boolean isParameterizedType = returnType instanceof ParameterizedType;

        if (!isParameterizedType) {
            throw new IllegalStateException("Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>");
        }

        //getParameterUpperBound(0, returnType);



        return null;
    }

    private class CoroutineCallAdapter<T, SUCCESS, ERROR> implements CallAdapter<T, ResultWrapper<SUCCESS, ERROR>> {
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

}
