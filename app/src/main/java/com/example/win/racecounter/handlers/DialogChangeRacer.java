package com.example.win.racecounter.handlers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.models.Racer;

public class DialogChangeRacer extends DialogFragment {
    private Racer changeddRacer;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.dialog_change_racer, null);

        EditText etId = (EditText) rootView.findViewById(R.id.etId);
        EditText etSurname = (EditText) rootView.findViewById(R.id.etSurname);
        EditText etName = (EditText) rootView.findViewById(R.id.etName);

        changeddRacer = MainActivity.arrayOfRacers.get(position);
        etId.setText(String.valueOf(changeddRacer.getId()));
        etSurname.setText(changeddRacer.getSurname());
        etName.setText(changeddRacer.getName());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setNeutralButton(R.string.dialog_change_racer_bt_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.arrayOfRacers.remove(position);
                        MainActivity.notifyAllAdapters();
                    }
                })
                .setPositiveButton(R.string.dialog_change_racer_bt_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etId = (EditText) rootView.findViewById(R.id.etId);
                        EditText etSurname = (EditText) rootView.findViewById(R.id.etSurname);
                        EditText etName = (EditText) rootView.findViewById(R.id.etName);

                        int racerId = Integer.parseInt(etId.getText().toString());
                        String surName = etSurname.getText().toString();
                        String name = etName.getText().toString();

                        changeddRacer.setId(racerId);
                        changeddRacer.setSurname(surName);
                        changeddRacer.setName(name);

                        MainActivity.notifyAllAdapters();
                    }
                })
                .setNegativeButton(R.string.dialog_add_racer_bt_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setPostition(int position){
        this.position = position;
    }
}
