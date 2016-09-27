package br.com.notifycar.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.notifycar.R;
import br.com.notifycar.repository.api.ListaUsuarioTask;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class ListaActivity extends AppCompatActivity{

    ListaUsuarioTask task = new ListaUsuarioTask(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listausr);

    }

    @Override
    protected void onStart() {
        super.onStart();
        task.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
