package br.com.notifycar.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.notifycar.repository.api.CadastraFcmIdUsuarioTask;

/**
 * Created by Desenvolvimento on 22/09/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseToken: ";
    private CadastraFcmIdUsuarioTask task;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "" + refreshedToken);

        task = new CadastraFcmIdUsuarioTask(refreshedToken,"0");
        new Thread(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        }).start();


        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

}
