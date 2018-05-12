package br.com.luisfernandez.github.client;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by luisfernandez on 12/05/18.
 */

public abstract class CallbackWrapper<SUCCESS> extends DisposableObserver<SUCCESS>
{
    public CallbackWrapper()
    {
    }

    @Override
    public void onNext(SUCCESS successType) {
        onSuccess(successType);
    }

    @Override
    public void onError(Throwable t) {

        onProblem(new ErrorWrapper(t));
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(SUCCESS success);

    protected abstract void onProblem(ErrorWrapper errorWrapper);
}