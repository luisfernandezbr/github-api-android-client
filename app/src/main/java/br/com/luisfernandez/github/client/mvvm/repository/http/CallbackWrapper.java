package br.com.luisfernandez.github.client.mvvm.repository.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import br.com.luisfernandez.github.client.BuildConfig;
import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.ServerError;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by luisfernandez on 12/05/18.
 */
public abstract class CallbackWrapper<SUCCESS, ERROR> extends DisposableObserver<SUCCESS> {
    private static final String TAG = "CallbackWrapper";

    private Class<ERROR> errorClass;

    public CallbackWrapper(Class<ERROR> errorClass) {
        this.errorClass = errorClass;
    }

    @Override
    public void onNext(SUCCESS successType) {
        onSuccess(successType);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            try {
                ERROR error = new Gson().fromJson(responseBody.string(), errorClass);
                ServerError<ERROR> errorServerError = new ServerError<>(error, ((HttpException) e).response().code(), null);
                onError(errorServerError);
            } catch (IOException e1) {
                handleError(2000, e);
            }
        } else if (e instanceof SocketTimeoutException) {
            handleError(3000, e);
        } else if (e instanceof IOException) {
            handleError(4000, e);
        } else {
            handleError(5000, e);
        }
    }

    private void handleError(int status, Throwable e) {
        Log.e(TAG, e.getMessage(), e);
        ServerError<ERROR> errorServerError = new ServerError<>(null, status, e.getMessage());
        onError(errorServerError);
    }

    @Override
    public void onComplete() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onComplete not implemented!");
        }
    }

    public abstract void onSuccess(SUCCESS success);

    public abstract void onError(ServerError<ERROR> error);
}