package br.com.notifycar.repository.api;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 27/09/2016.
 */
public class AlteraUsuarioTask extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL("http://notifycar-api.mybluemix.net/gateway/register/494101363422774");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {


            }
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

}
