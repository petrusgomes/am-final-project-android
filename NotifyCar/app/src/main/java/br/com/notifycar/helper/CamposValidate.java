package br.com.notifycar.helper;

import android.app.Activity;
import android.widget.EditText;

import br.com.notifycar.R;

/**
 * Created by Desenvolvimento on 26/09/2016.
 */
public class CamposValidate {

    private EditText edtLoginEmail;
    private EditText edtLoginSenha;

    public boolean validaCamposLogin(Activity activity)
    {
        edtLoginEmail = (EditText) activity.findViewById(R.id.edtEmailLogin);
        edtLoginSenha = (EditText) activity.findViewById(R.id.edtSenhaLogin);

        String email = edtLoginEmail.getText().toString();
        String senha = edtLoginSenha.getText().toString();

        if (email.equals("") && senha.equals("")){
            edtLoginEmail.requestFocus();
            edtLoginEmail.setError("Campo login obrigatorio");

            edtLoginSenha.requestFocus();
            edtLoginSenha.setError("Campo senha obrigatorio");

            return false;
        } else if(senha.equals("")){
            edtLoginSenha.requestFocus();
            edtLoginSenha.setError("Campo senha obrigatorio");

            return false;
        } else if(email.equals("")){
            edtLoginEmail.requestFocus();
            edtLoginEmail.setError("Campo login obrigatorio");

            return false;
        } else {
            return true;
        }

    }


}
