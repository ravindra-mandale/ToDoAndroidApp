package com.programminginmyway.todoappnew;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class ShowAlertDialog {

    public static AlertDialog.Builder createAlertDialogForCloseApp(@NonNull Context context) {
        //Creational design pattern- Builder pattern example
        return new AlertDialog.Builder(context)
                .setTitle(R.string.alert_dialog_title_exit)
                .setMessage(R.string.alert_dialog_message_exit)
                .setCancelable(false);
    }
}
