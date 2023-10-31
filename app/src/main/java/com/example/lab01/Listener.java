package com.example.lab01;

import android.graphics.Bitmap;

public interface Listener {
    void onImageLoader(Bitmap bitmap);
    void onError();
}
