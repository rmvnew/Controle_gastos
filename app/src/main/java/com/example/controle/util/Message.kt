package com.example.controle.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


object Message{
        fun toast(context: Context,message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

        fun snack(view:View,message: String){
            val snackbar = Snackbar.make(view,message,3000).setAction("action",null)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.BLACK)
            snackbar.show()
        }
   }

