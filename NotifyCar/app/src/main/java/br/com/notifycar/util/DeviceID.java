package br.com.notifycar.util;

import android.app.Activity;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.media.RemotePlaybackClient;


/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class DeviceID extends AppCompatActivity{

    private Activity activity;

    public DeviceID(Activity activity){
        this.activity = activity;
    }

    public String retornaIdDevice(){

        String android_id = Secure.getString(activity.getContentResolver(),
            Secure.ANDROID_ID);

        return android_id;
    }

}
