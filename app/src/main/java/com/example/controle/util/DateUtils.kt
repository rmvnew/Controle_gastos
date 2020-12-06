package com.example.controle.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.controle.model.Consumer
import com.example.controle.model.Expenses
import com.example.controle.model.Product
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class DateUtils() : BaseFragment(){


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
        fun getSpinnerCurrentYear(listaDeAnos: List<String>):Int{

            val calendar = Calendar.getInstance()
            val ano = calendar.get(Calendar.YEAR)
            var anoAtual = 0
            for((index,item) in listaDeAnos.withIndex()){

                if(ano == listaDeAnos[index].toInt()){
                    anoAtual = index
                }

            }




            return anoAtual
        }

        fun getYear(string: String):Int{
            val teste:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val data:Date = teste.parse(string)
            val cal = Calendar.getInstance()
            cal.time = data
            val anoNovo = cal.get(Calendar.YEAR)

            return anoNovo
        }

        fun getStrYear(string: String):String{
            val teste:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val data = teste.parse(string)
            val cal = Calendar.getInstance()
            cal.time = data
            val anoNovo = cal.get(Calendar.YEAR)

            return anoNovo.toString()
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
                "Nov" -> numeroMes = 10
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

                       "Jan" -> valorMeses[0] += item.valor.replace(",",".").toDouble()
                       "Fev" -> valorMeses[1] += item.valor.replace(",",".").toDouble()
                       "Mar" -> valorMeses[2] += item.valor.replace(",",".").toDouble()
                       "Abr" -> valorMeses[3] += item.valor.replace(",",".").toDouble()
                       "Mai" -> valorMeses[4] += item.valor.replace(",",".").toDouble()
                       "Jun" -> valorMeses[5] += item.valor.replace(",",".").toDouble()
                       "Jul" -> valorMeses[6] += item.valor.replace(",",".").toDouble()
                       "Ago" -> valorMeses[7] += item.valor.replace(",",".").toDouble()
                       "Set" -> valorMeses[8] += item.valor.replace(",",".").toDouble()
                       "Out" -> valorMeses[9] += item.valor.replace(",",".").toDouble()
                       "Nov" -> valorMeses[10] += item.valor.replace(",",".").toDouble()
                       "Dez" -> valorMeses[11] += item.valor.replace(",",".").toDouble()
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
                        "Jan" -> valorMeses[0] += item.consumer.replace(",",".").toDouble()
                        "Fev" -> valorMeses[1] += item.consumer.replace(",",".").toDouble()
                        "Mar" -> valorMeses[2] += item.consumer.replace(",",".").toDouble()
                        "Abr" -> valorMeses[3] += item.consumer.replace(",",".").toDouble()
                        "Mai" -> valorMeses[4] += item.consumer.replace(",",".").toDouble()
                        "Jun" -> valorMeses[5] += item.consumer.replace(",",".").toDouble()
                        "Jul" -> valorMeses[6] += item.consumer.replace(",",".").toDouble()
                        "Ago" -> valorMeses[7] += item.consumer.replace(",",".").toDouble()
                        "Set" -> valorMeses[8] += item.consumer.replace(",",".").toDouble()
                        "Out" -> valorMeses[9] += item.consumer.replace(",",".").toDouble()
                        "Nov" -> valorMeses[10] += item.consumer.replace(",",".").toDouble()
                        "Dez" -> valorMeses[11] += item.consumer.replace(",",".").toDouble()
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