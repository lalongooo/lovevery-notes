package com.lovevery.notes.android.utility

import android.view.inputmethod.EditorInfo

fun isDoneButtonPressed(action: Int): Boolean {
    return action == EditorInfo.IME_ACTION_DONE ||
        action == EditorInfo.IME_ACTION_GO ||
        action == EditorInfo.IME_ACTION_NEXT ||
        action == EditorInfo.IME_ACTION_SEND
}
