package com.example.win.racecounter.handlers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;

public class DialogDeleteRacerLap  extends DialogFragment {
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete racer " + getArguments().getString("to_del") + "?")
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                        //Bundle args = new Bundle();
                        //args.putBoolean("to_del_confirm", true);
                        getArguments().putBoolean("to_del_confirm", true);

                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Bundle args = new Bundle();
                        args.putBoolean("to_del_confirm", false);
                    }
                });
        return  builder.create();
    }
}