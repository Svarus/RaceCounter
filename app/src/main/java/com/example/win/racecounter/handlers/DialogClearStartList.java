package com.example.win.racecounter.handlers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;

public class DialogClearStartList extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_clear_start_list)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.arrayOfRacers.clear();
                        MainActivity.arrayOfRacersProtocol.clear();
                        MainActivity.arrayOfRacersSorted.clear();
                        MainActivity.notifyAllAdapters();
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return  builder.create();
    }
}
