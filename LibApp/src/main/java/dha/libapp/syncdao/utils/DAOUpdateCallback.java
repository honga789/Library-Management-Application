package dha.libapp.syncdao.utils;

public interface DAOUpdateCallback {
    void onSuccess();
    void onError(Throwable e);
}
