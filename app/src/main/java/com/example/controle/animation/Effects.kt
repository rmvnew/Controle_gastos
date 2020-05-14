package com.example.controle.animation

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.example.controle.R


class Effects {

    companion object{
        //efeito de clique no botão =)
        fun clickEffect(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.btn_touch_01)
            view.startAnimation(effect)
        }

        //efeito aparecer
        fun fadeInEffect(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            view.startAnimation(effect)
        }

        //efeito rodar
        fun rotateEffect(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.rotate)
            view.startAnimation(effect)
        }

        //aparecer de cima
        fun aparecerDeCima(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            view.startAnimation(effect)
        }


        //aparecer de baixo
        fun aparecerDebaixo(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            view.startAnimation(effect)
        }

        //crescer
        fun crescer(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
            view.startAnimation(effect)
        }

        //diminuir
        fun diminuir(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
            view.startAnimation(effect)
        }


        //vir da esquerda
        fun virDaEsquerda(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.slide_left)
            view.startAnimation(effect)
        }

        //vir da direita
        fun virDaDireita(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.slide_rigth)
            view.startAnimation(effect)
        }

        //alpha
        fun efeitoFundo01(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.fade_fundo01)
            view.startAnimation(effect)
        }

        //alpha
        fun efeitoFundo02(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.fade_fundo02)
            view.startAnimation(effect)
        }


        fun girarDireita(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.gira_direita)
            view.startAnimation(effect)
        }

        fun girarEsquerda(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.gira_esquerda)
            view.startAnimation(effect)
        }

        fun pulso(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.pulse)
            view.startAnimation(effect)
        }

    }

}