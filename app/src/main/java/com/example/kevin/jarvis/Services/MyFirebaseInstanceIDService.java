package com.example.kevin.jarvis.Services;

import android.util.Log;
import android.widget.Toast;

import com.example.kevin.jarvis.Tools.ApiCall;
import com.example.kevin.jarvis.Tools.RetrofitInstance;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevin on 13/04/2017.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        final String url = "http://jarvis-esgi.herokuapp.com/api/";
        RetrofitInstance.getInstance(url).create(ApiCall.class).register("android",token).enqueue
                (new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("RESPONSE : ", "" + response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("ERROR RESPONSE : ", "" + t.getMessage());
                        Toast.makeText(getApplicationContext(),"Token is not refreshed !",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
