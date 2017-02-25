package com.example.martin.studievolg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;

import java.util.Locale;

/**
 * Created by Martin on 26-1-2017.
 */

public class EditCijfer extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Voer cijfer in:");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.edit_cijfer_fragment, null))
                // Add action buttons
                .setPositiveButton("Wijzigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText editText = (EditText) getDialog().findViewById(R.id.editText);
                        String cijfer = editText.getText().toString();
                        cijfer = cijfer.replace(",", ".");
                        if(Double.parseDouble(cijfer) > 10) {
                            Toast.makeText(getActivity(), R.string.foutiefCijfer, Toast.LENGTH_SHORT).show();
                        }
                        else if(Double.parseDouble(cijfer) > 5.4 && Double.parseDouble(cijfer) < 10) {
                            Toast.makeText(getActivity(), R.string.voldoende, Toast.LENGTH_SHORT).show();
                            cijferInvoeren(cijfer);
                        }
                        else if(Double.parseDouble(cijfer) < 5.5 && Double.parseDouble(cijfer) < 10) {
                            Toast.makeText(getActivity(), R.string.onvoldoende, Toast.LENGTH_SHORT).show();
                            cijferInvoeren(cijfer);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditCijfer.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void showMessage(String message) {
        Toast t = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        t.show();
    }

    private void cijferInvoeren(String cijfer) {
        Bundle bundle = getArguments();
        String course = bundle.getString("name");

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getActivity());
        ContentValues newValues = new ContentValues();
        newValues.put("grade", cijfer);
        dbHelper.update(DatabaseInfo.CourseTables.COURSETABLE, newValues, "name=?", new String[]{course});
        String message = getString(R.string.nieuwCijfer) + course;
        showMessage(message);
        restartActivity();
    }

    private void restartActivity() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
    }
}