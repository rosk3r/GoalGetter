package ru.rosk3r.composetest.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

fun myToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}