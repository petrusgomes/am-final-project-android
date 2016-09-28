//POR ENQUANTO SEM USO!!

//package br.com.notifycar.repository.api;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import org.json.JSONObject;
//
//import java.io.DataOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import br.com.notifycar.util.UtilJson;
//
///**
// * Created by Desenvolvimento on 27/09/2016.
// */
//public class AlteraUsuarioTask extends AsyncTask<String, Void, String> {
//
//
//    private String idUsuario;
//    private String deviceToken;
//    private Activity activity;
//
//    public AlteraUsuarioTask(String idUsuario, String deviceToken, Activity activity){
//        this.idUsuario = idUsuario;
//        this.deviceToken = deviceToken;
//        this.activity = activity;
//    }
//
//
//    @Override
//    protected String doInBackground(String... params) {
//
//        URL url = null;
//        try {
//            url = new URL("http://notifycar-api.mybluemix.net/usuario/"+idUsuario);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("PUT");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("charset", "utf-8");
//            conn.setDoOutput(true);
//            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//            JSONObject usuario = new JSONObject();
//
//            usuario.put("deviceToken", deviceToken);
//
//            out.writeBytes(usuario.toString());
//            out.flush();
//            out.close();
//
//        }catch (Exception e){
//            e.getMessage();
//        }
//        return null;
//    }
//
//
//    @Override
//    protected void onPostExecute(String s) {
//
//        Toast.makeText(activity, "VEICULO ALTERADO COM SUCESSO", Toast.LENGTH_LONG).show();
//
//    }
//}
