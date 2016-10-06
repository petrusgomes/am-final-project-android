package br.com.notifycar.repository.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 25/09/2016.
 */
public class BloqueioDesbloqueioVeiculoTask extends AsyncTask<String, Void, String> {

    private String aux;
    private String urlSafe;
    private Activity activity;
    private HttpURLConnection conn;

    public BloqueioDesbloqueioVeiculoTask(String urlSafe, String aux, Activity activity){
        this.aux = aux;
        this.urlSafe = urlSafe;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(urlSafe + "/" + aux);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            out.writeBytes("");

            out.flush();
            out.close();

            Log.i("sasa", conn.getInputStream().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        String lock = "lock";
        String reset = "reset";

        if(aux.equals(lock)) {
            Toast.makeText(activity, "Veiculo Bloqueado com Sucesso!", Toast.LENGTH_LONG).show();
        } else if(aux.equals(reset)){
            Toast.makeText(activity, "Veiculo Desbloqueado com Sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "ERRO!", Toast.LENGTH_LONG).show();
        }
    }
}
