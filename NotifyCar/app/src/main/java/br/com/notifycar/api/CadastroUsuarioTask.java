package br.com.notifycar.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import br.com.notifycar.helper.CamposHelper;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class CadastroUsuarioTask extends AsyncTask<String, Void, String> {

    private Activity activity;
    private ProgressDialog progressDialog;
    CamposHelper helper = new CamposHelper();

    public CadastroUsuarioTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Carregando...");
        progressDialog.setMessage("Aguarde");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL("http://notifycar-api.mybluemix.net/usuario");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());


            out.writeBytes(helper.recuperaCamposUsuario(activity).toString());

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
        Toast.makeText(activity, "VEICULO CADASTRO COM SUCESSO", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}
