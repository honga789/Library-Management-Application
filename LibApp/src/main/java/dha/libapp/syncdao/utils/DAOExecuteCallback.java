package dha.libapp.syncdao.utils;

public interface DAOExecuteCallback<T> {
    void onSuccess(T result);
    void onError(Throwable e);
}
