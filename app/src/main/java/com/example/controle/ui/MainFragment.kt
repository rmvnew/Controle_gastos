package com.example.controle.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.Expenses
import com.example.controle.util.DateUtils
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.controle.model.Product
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment() {

    val handler = Handler()
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

setHasOptionsMenu(true)

        val currentContext = inflater.inflate(R.layout.fragment_main, container, false)

       // setupPieChart()

        (activity as MainActivity).supportActionBar?.setTitle("Principal")
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

        return currentContext
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        btn_usuario.setOnClickListener {
            Effects.clickEffect(btn_usuario,activity!!)
            val action = MainFragmentDirections.actionHomeToUsuariosCadastrados()
            Navigation.findNavController(it).navigate(action)
        }



        btn_historico.setOnClickListener {
            Effects.clickEffect(btn_historico,activity!!)
            val action = MainFragmentDirections.actionMainToList()
            Navigation.findNavController(it).navigate(action)
        }

        btn_despesas.setOnClickListener {
            Effects.clickEffect(btn_despesas,activity!!)

            val action = MainFragmentDirections.actionMainToAdd()
            Navigation.findNavController(it).navigate(action)

        }

        btn_consumo.setOnClickListener {
            Effects.clickEffect(btn_consumo,activity!!)
            val action = MainFragmentDirections.actionHomeToAgua()
            Navigation.findNavController(it).navigate(action)

        }


        var options = DateUtils.getYears()




        spinnerMain.adapter =
            context?.let {
                ArrayAdapter<String>(it,android.R.layout.simple_list_item_1,options)
            }

        spinnerMain.setSelection(DateUtils.getSpinnerCurrentYear())
        spinnerMain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val valor =  spinnerMain.selectedItem.toString()

                setupPieChart(valor)


            }
        }





    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPieChart(string: String){



        launch {
            context.let {

                var barEntries = ArrayList<BarEntry>()
                val valor = ProductDatabase(it!!).getProductDao().getAllProducts()
                val listaMeses = DateUtils.getExpenses(valor,string)

                for ((index,value) in listaMeses.withIndex()){

                    barEntries.add(BarEntry(listaMeses[index].value.toFloat(),DateUtils.getMonthInteger(listaMeses[index].expenses)))

                }

                var dataset = BarDataSet(barEntries,"Despesas")
                dataset.setColors(ColorTemplate.COLORFUL_COLORS)
                dataset.valueTextSize = 10f
                val labels = listaMeses.map { Product -> Product.expenses}

                val data = BarData(labels, dataset)
                barChart.setDescription("Despesas")
                barChart.data = data
                barChart.invalidate()
                barChart.animateY(1800)

            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        AjudaFragment.setNumber(0)

        when(item.itemId){
            R.id.men_ajuda -> findNavController().navigate(R.id.actionMainToAjuda)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }



}
