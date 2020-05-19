package com.example.controle.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.controle.R
import com.example.controle.animation.Effects
import kotlinx.android.synthetic.main.activity_open.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

class OpenActivity : AppCompatActivity() {

    val hand = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        val expense = findViewById<ImageView>(R.id.txt_expenses)
        val manager = findViewById<ImageView>(R.id.txt_manager)
        val grafico = findViewById<ImageView>(R.id.txt_grafico)
        txt_versao.setText("Versão: 1.20")

        Effects.virDaDireita(expense,this)
        Effects.virDaEsquerda(manager,this)
        Effects.crescer(grafico,this)
        Effects.aparecerDebaixo(txt_versao,this)





        hand.postDelayed(Runnable {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
            finish()
        },2000)


    }





}
