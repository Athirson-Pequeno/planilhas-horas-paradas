package com.example.planilhahorasparadas.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardController {
    public static void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) MyApplicationContext.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  MyApplicationContext.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
