package dha.libapp.utils.API.Image;

import javafx.scene.image.Image;

public interface ImageFetchCallback {
    void onSuccess(Image image);
    void onFailure(Exception ex);
}
