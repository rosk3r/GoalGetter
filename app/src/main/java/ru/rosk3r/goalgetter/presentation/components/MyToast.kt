package ru.rosk3r.goalgetter.presentation.components

import android.content.Context
import android.widget.Toast

fun myToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}