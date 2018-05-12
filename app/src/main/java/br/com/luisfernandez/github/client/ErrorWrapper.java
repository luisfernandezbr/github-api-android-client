package br.com.luisfernandez.github.client;

/**
 * Created by luisfernandez on 12/05/18.
 */

public class ErrorWrapper
{
    private Throwable throwable;

    public ErrorWrapper(Throwable throwable)
    {
        this.throwable = throwable;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }
}
