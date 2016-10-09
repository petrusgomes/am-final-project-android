package br.com.notifycar.repository.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.controller.CadastroDeviceActivity;
import br.com.notifycar.controller.CadastroVeiculoActivity;
import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 06/10/2016.
 */
public class CadastroVeiculoTask extends AsyncTask<String, Void, String> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String json = "";
    private String usuarioId;
    CamposHelper helper = new CamposHelper();

    public CadastroVeiculoTask(Activity activity, String usuarioId){
        this.activity = activity;
        this.usuarioId = usuarioId;
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
            URL url = new URL("http://notifycar-api.mybluemix.net/veiculo");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());


            out.writeBytes(helper.recuperaCamposVeiculo(activity,usuarioId).toString());

            out.flush();
            out.close();


            InputStream conteudo = conn.getInputStream();
            json = UtilJson.toString(conteudo);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json) {
    String veiculoId = "";
        try {
            JSONObject veiculo = new JSONObject(json);
            veiculoId = veiculo.getString("_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(activity, "Veiculo cadastrado com Sucesso! ", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

        Intent it = new Intent(activity, CadastroDeviceActivity.class);
        it.putExtra("veiculoId", veiculoId);
        activity.startActivity(it);

    }
}
