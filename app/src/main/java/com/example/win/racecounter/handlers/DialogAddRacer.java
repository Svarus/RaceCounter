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

public class DialogAddRacer extends DialogFragment{
    private Racer addedRacer;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.dialog_add_racer, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(R.string.dialog_add_racer_bt_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        EditText etId = (EditText) rootView.findViewById(R.id.etId);
                        EditText etSurname = (EditText) rootView.findViewById(R.id.etSurname);
                        EditText etName = (EditText) rootView.findViewById(R.id.etName);

                        int racerId = Integer.parseInt(etId.getText().toString());
                        String surName = etSurname.getText().toString();
                        String name = etName.getText().toString();

                        Racer newRacer = new Racer(racerId, surName, name, 0, 0);
                        MainActivity.arrayOfRacers.add(newRacer);
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

}
