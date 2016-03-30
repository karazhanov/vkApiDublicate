package com.vk.music.vkmusiccopy;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by karazhanov on 25.03.16.
 */
public class VkMusicApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(this);
    }
}
