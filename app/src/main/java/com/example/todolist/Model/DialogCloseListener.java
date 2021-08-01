package com.example.todolist.Model;

import android.content.DialogInterface;
import android.view.Menu;

public interface DialogCloseListener {
    public void handleDialogClose(DialogInterface dialog);

    boolean onCreatOptionMenu(Menu menu);
}
