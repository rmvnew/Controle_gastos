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

        //efeito aparecer
        fun rotateEffect(view: View, context: Context){
            var effect = AnimationUtils.loadAnimation(context, R.anim.rotate)
            view.startAnimation(effect)
        }

    }

}