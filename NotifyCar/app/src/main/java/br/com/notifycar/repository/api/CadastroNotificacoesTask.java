package br.com.notifycar.repository.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Desenvolvimento on 05/10/2016.
 */
public class CadastroNotificacoesTask extends AsyncTask<String, Void, String> {


    private String titulo;
    private String mensagem;
    private String usuarioId;

    public CadastroNotificacoesTask(String titulo, String Mensagem, String usuarioId){
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.usuarioId = usuarioId;
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL("http://notifycar-api.mybluemix.net/notificacoes/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            JSONObject notificacao = new JSONObject();
            notificacao.put("titulo", titulo);
            notificacao.put("mensagem", mensagem);
            notificacao.put("usuarioId", usuarioId);

            out.writeBytes(notificacao.toString());

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
}
