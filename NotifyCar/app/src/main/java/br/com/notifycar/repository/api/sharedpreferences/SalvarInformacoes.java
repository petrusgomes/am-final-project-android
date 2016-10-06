package br.com.notifycar.repository.api.sharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Desenvolvimento on 04/10/2016.
 */
public class SalvarInformacoes {

    private Activity activity;

    public  SalvarInformacoes(Activity activity){
        this.activity = activity;

    }

    public String getEmailLogin(){
        SharedPreferences preferences = activity.getSharedPreferences("emailLogin", android.content.Context.MODE_PRIVATE);
        return preferences.getString("emailLogin", "");
    }

    public void salvarInfoLogin(String emailLogin){
        SharedPreferences preferences = activity.getSharedPreferences("emailLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("emailLogin", emailLogin);
        editor.commit();
    }



}
