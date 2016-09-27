package br.com.notifycar.menu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import br.com.notifycar.R;
import br.com.notifycar.repository.api.ListaUrlSafeTask;

/**
 * Created by Ratan on 7/29/2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private Button btnMapa;
    private ListaUrlSafeTask task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, null, false);


        btnMapa = (Button) v.findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMapa:
                task = new ListaUrlSafeTask(getActivity());
                task.execute();
                break;
            default:
                break;
        }
    }
}
