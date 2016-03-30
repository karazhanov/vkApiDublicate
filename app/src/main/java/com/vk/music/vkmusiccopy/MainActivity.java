package com.vk.music.vkmusiccopy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.e("prints", fingerprints[0]);

        VKSdk.login(this, VKScope.AUDIO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Log.e("AUTH", "SUCCES");
                VKRequest vkRequest = VKApi.audio().get(VKParameters.from(VKApiConst.OWNER_ID, "123865455"));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
//Do complete stuff
                        Log.e("VKApi.audio", response.responseString);
                        JsonObject asJsonObject = new JsonParser().parse(response.responseString).getAsJsonObject();
                        JsonObject response1 = asJsonObject.get("response").getAsJsonObject();
                        JsonArray items = response1.get("items").getAsJsonArray();
                        for (JsonElement item : items) {
                            JsonObject asJsonObject1 = item.getAsJsonObject();
                            Log.e("ERR", asJsonObject1.toString());
//                            {"id":456239018,"owner_id":245446811,"artist":"Мельница","title":"Прощай (Алхимия 2015)","duration":233,"date":1458740738,"url":"https://cs7-3v4.vk-cdn.net/p4/bffd7e9aeec7bf.mp3?extra=BLfpGfUTbESXXNg26mSKRgDASFZIorsQ0gDjJM5wmT6qr6UORgAZXGc7-gaxyq9UfWSN7BbR409g5tashdtTCDbscexxeyIgGb3wSgcmuxP4zK3vrfLfjXf2s7Ufhr9GOjn-CJEStFHBkjc","lyrics_id":297989431,"genre_id":13}
                        }
                            VKRequest audio_id = VKApi.audio().add(VKParameters.from(VKApiConst.OWNER_ID, "123865455", "audio_id", "456239028"));
                                audio_id.executeWithListener(new VKRequest.VKRequestListener() {
                                    @Override
                                    public void onComplete(VKResponse response) {
                                        Log.e("VKApi.audio.add", response.responseString);
                                    }
                                    @Override
                                    public void onError(VKError error) {
                                        Log.e("VKApi.audio.add", error.toString());
                                    }
                                    @Override
                                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                                        Log.e("VKApi.audio.add", request.toString());
                                    }
                                });
                    }
                    @Override
                    public void onError(VKError error) {
//Do error stuff
                        Log.e("VKApi.audio", error.toString());
                    }
                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//I don't really believe in progress
                        Log.e("VKApi.audio", request.toString());
                    }
                });
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Log.e("AUTH", "ERROR");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
