package br.com.notifycar.repository.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Desenvolvimento on 29/09/2016.
 */
public class AlteraFcmIdUsuarioTask extends AsyncTask<String,Void,String> {

    private String emailUsuario;

    public AlteraFcmIdUsuarioTask(String emailUsuario){
        this.emailUsuario = emailUsuario;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL("http://notifycar-api.mybluemix.net/firebase/"+emailUsuario);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            JSONObject fire = new JSONObject();
            fire.put("emailUsuario", emailUsuario);

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
