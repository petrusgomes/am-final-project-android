package br.com.notifycar.repository.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Desenvolvimento on 28/09/2016.
 */
public class CadastraFcmIdUsuarioTask extends AsyncTask<String, Void, String> {

    private String fcmId;
    private String emailUsuario;

    public CadastraFcmIdUsuarioTask(String fcmId, String emailUsuario){
        this.fcmId = fcmId;
        this.emailUsuario = emailUsuario;

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL("http://notifycar-api.mybluemix.net/firebase/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            JSONObject fire = new JSONObject();
            fire.put("emailUsuario", emailUsuario);
            fire.put("fcmId", fcmId);

            out.writeBytes(fire.toString());

            out.flush();
            out.close();

            if(conn.getResponseCode() == 200){
                Log.i("TESTE", "aaaaaaaaaaaaaaaa");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    @Override
    protected void onPostExecute(String s) {
        Log.i("FREFE", "FIRE INSERIDO COM SUCESSO ******************");
    }
}
