package com.henallux.androidproject.Java.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.henallux.androidproject.R;

import java.util.List;

/**
 * Created by milou_000 on 21-10-15.
 */
public class ListClassement extends DialogFragment {
    ListView listClassement;
    Button backButton;
    String[] players = {"Zak 1555 pts","Francois 1550 pts","Julien 1490 pts","Abdel 1430 pts","Legende 1425 pts","Quentin 1411 pts","Maxence 1401 pts",
            "Antoine 1345 pts"};
    ArrayAdapter<String> adapter;
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.classements, null);
        getDialog().setTitle("Classement");
        backButton = (Button)rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        listClassement = (ListView) rootView.findViewById(R.id.listClassements);
        adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, players);
        listClassement.setAdapter(adapter);
        return rootView;

    }
}
