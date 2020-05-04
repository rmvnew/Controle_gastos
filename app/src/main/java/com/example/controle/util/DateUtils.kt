package com.example.controle.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.controle.model.Consumer
import com.example.controle.model.Expenses
import com.example.controle.model.Product
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class DateUtils() {


    companion object{

        val meses = arrayOf(
            "Jan","Fev","Mar","Abr","Mai","Jun",
        "Jul","Ago","Set","Out","Nov","Dez"
        )

        val valorMeses = arrayOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)


    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonth(string: String):String{


        val teste:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val data:Date = teste.parse(string)
        val numeroMes = data.month.absoluteValue

        return getMes(numeroMes)
    }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getSpinnerCurrentYear():Int{

            val teste:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val data = LocalDateTime.now()
            val ano = data.year.toString().substring(2)
            Log.i("TesteAno",ano.toString())
            return (ano.toInt()-1)
        }

        fun getYear(string: String):Int{
            val teste:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val data:Date = teste.parse(string)
            val cal = Calendar.getInstance()
            cal.time = data
            val anoNovo = cal.get(Calendar.YEAR)

            return anoNovo
        }



        @RequiresApi(Build.VERSION_CODES.O)
        fun getMonthInteger(string: String):Int{


            var numeroMes:Int = 0

            when(string){
                "Jan" -> numeroMes = 0
                "Fev" -> numeroMes = 1
                "Mar" -> numeroMes = 2
                "Abr" -> numeroMes = 3
                "Mai" -> numeroMes = 4
                "Jun" -> numeroMes = 5
                "Jul" -> numeroMes = 6
                "Ago" -> numeroMes = 7
                "Set" -> numeroMes = 8
                "Out" -> numeroMes = 9
                "Nav" -> numeroMes = 10
                "Dez" -> numeroMes = 11
            }


            return numeroMes
        }

        fun getMes(numeroMes: Int):String{
            var mesEscolhido = ""
            when(numeroMes){
                0 -> mesEscolhido = "Jan"
                1 -> mesEscolhido = "Fev"
                2 -> mesEscolhido = "Mar"
                3 -> mesEscolhido = "Abr"
                4 -> mesEscolhido = "Mai"
                5 -> mesEscolhido = "Jun"
                6 -> mesEscolhido = "Jul"
                7 -> mesEscolhido = "Ago"
                8 -> mesEscolhido = "Set"
                9 -> mesEscolhido = "Out"
                10 -> mesEscolhido = "Nov"
                11 -> mesEscolhido = "Dez"
            }
            return mesEscolhido
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun getExpenses(valor: List<Product>,ano: String): ArrayList<Expenses> {


           val listaDeItem = arrayListOf<Expenses>()



           for(item in valor){

               if(getYear(item.data) == ano.toInt()) {

                   when (getMonth(item.data)) {

                       "Jan" -> valorMeses[0] += item.valor.toDouble()
                       "Fev" -> valorMeses[1] += item.valor.toDouble()
                       "Mar" -> valorMeses[2] += item.valor.toDouble()
                       "Abr" -> valorMeses[3] += item.valor.toDouble()
                       "Mai" -> valorMeses[4] += item.valor.toDouble()
                       "Jun" -> valorMeses[5] += item.valor.toDouble()
                       "Jul" -> valorMeses[6] += item.valor.toDouble()
                       "Ago" -> valorMeses[7] += item.valor.toDouble()
                       "Set" -> valorMeses[8] += item.valor.toDouble()
                       "Out" -> valorMeses[9] += item.valor.toDouble()
                       "Nov" -> valorMeses[10] += item.valor.toDouble()
                       "Dez" -> valorMeses[11] += item.valor.toDouble()
                   }
               }


           }


            for((index, inteiro) in meses.withIndex()){
                listaDeItem.add(Expenses(meses[index], valorMeses[index].toString()))
            }

          limpar(valorMeses)

            return listaDeItem


        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getConsumer(valor: List<Product>,string: String,ano:String): ArrayList<Consumer> {


            val listaDeItem = arrayListOf<Consumer>()



            for(item in valor){

                if(item.nome.contains(string) && (getYear(item.data) == ano.toInt())) {

                    when (getMonth(item.data)) {
                        "Jan" -> valorMeses[0] += item.consumer.toDouble()
                        "Fev" -> valorMeses[1] += item.consumer.toDouble()
                        "Mar" -> valorMeses[2] += item.consumer.toDouble()
                        "Abr" -> valorMeses[3] += item.consumer.toDouble()
                        "Mai" -> valorMeses[4] += item.consumer.toDouble()
                        "Jun" -> valorMeses[5] += item.consumer.toDouble()
                        "Jul" -> valorMeses[6] += item.consumer.toDouble()
                        "Ago" -> valorMeses[7] += item.consumer.toDouble()
                        "Set" -> valorMeses[8] += item.consumer.toDouble()
                        "Out" -> valorMeses[9] += item.consumer.toDouble()
                        "Nov" -> valorMeses[10] += item.consumer.toDouble()
                        "Dez" -> valorMeses[11] += item.consumer.toDouble()
                    }

                }

            }


            for((index, inteiro) in meses.withIndex()){
                listaDeItem.add(Consumer(meses[index], valorMeses[index].toString()))
            }

            limpar(valorMeses)

            return listaDeItem


        }

        fun limpar(lista:Array<Double>){
            for((index,inteiro) in lista.withIndex()){
                valorMeses[index] = 0.0
            }
        }

        fun getYears():Array<String?>{
            var listaDeAnos = arrayOfNulls<String>(100)

            for(i in 0..99){
                listaDeAnos[i] = (2001+i).toString()
            }

            return listaDeAnos
        }


    }

}