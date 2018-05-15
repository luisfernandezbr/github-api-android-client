package br.com.luisfernandez.github.client.http;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import br.com.luisfernandez.github.client.http.model.ServerError;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by luisfernandez on 12/05/18.
 */
public abstract class CallbackWrapper<SUCCESS, ERROR> extends DisposableObserver<SUCCESS>
{
    private static final String TAG = "CallbackWrapper";

    private Class<ERROR> errorClass;

    public CallbackWrapper(Class<ERROR> errorClass)
    {
        this.errorClass = errorClass;
    }

    @Override
    public void onNext(SUCCESS successType) {
        onSuccess(successType);
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException)e).response().errorBody();
            try
            {
                ERROR error = new Gson().fromJson(responseBody.string(), errorClass);
                ServerError<ERROR> errorServerError = new ServerError<>(error, ((HttpException) e).response().code(), null);
                onError(errorServerError);
            }
            catch (IOException e1)
            {
                ServerError<ERROR> errorServerError = new ServerError<>(null, 5000, "Generic Error");
                onError(errorServerError);
            }
        } else if (e instanceof SocketTimeoutException) {
            ServerError<ERROR> errorServerError = new ServerError<>(null, 3000, ((SocketTimeoutException) e).getMessage());
            onError(errorServerError);
        } else if (e instanceof IOException) {
            ServerError<ERROR> errorServerError = new ServerError<>(null, 4000, ((IOException) e).getMessage());
            onError(errorServerError);
        } else {
            ServerError<ERROR> errorServerError = new ServerError<>(null, 5000, "Generic Error");
            onError(errorServerError);
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete not implemented!");
    }

    public abstract void onSuccess(SUCCESS success);

    public abstract void onError(ServerError<ERROR> error);
}