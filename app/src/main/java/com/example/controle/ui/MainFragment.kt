package com.example.controle.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.util.DateUtils
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import android.content.ClipData
import android.content.ClipboardManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.controle.model.Product
import com.example.controle.util.AjudaFragment
import com.example.controle.util.BaseFragment
import com.example.controle.util.DateUtilsJava
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setTitle("Principal")
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_usuario.setOnClickListener {
            Effects.clickEffect(btn_usuario, activity!!)
            findNavController().navigate(R.id.actionHomeToUsuariosCadastrados)
        }

        btn_historico.setOnClickListener {
            Effects.clickEffect(btn_historico, activity!!)
            findNavController().navigate(R.id.actionMainToList)
        }

        btn_despesas.setOnClickListener {
            Effects.clickEffect(btn_despesas, activity!!)
            findNavController().navigate(R.id.actionMainToAdd)
        }

        btn_consumo.setOnClickListener {
            Effects.clickEffect(btn_consumo, activity!!)
            findNavController().navigate(R.id.actionHomeToAgua)
        }

        btn_fab_settings.setOnClickListener {
            Effects.clickEffect(btn_fab_settings, activity!!)
            findNavController().navigate(R.id.actionMainToPassword)
        }

        btn_OnusFixo.setOnClickListener {
            Effects.clickEffect(btn_OnusFixo, activity!!)
            findNavController().navigate(R.id.actionMainToAddRegistro)
        }

        btn_list_fixed_expenses.setOnClickListener {
            Effects.clickEffect(btn_list_fixed_expenses, activity!!)
            findNavController().navigate(R.id.actionMainToListFixedExpenses)
        }

        listaDeAnos()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSpinner(listaDeAnos: List<String>) {
        spinnerMain.adapter =
            context?.let {

                ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, listaDeAnos)

            }

        //setar ano atual
        spinnerMain.setSelection(DateUtils.getSpinnerCurrentYear(listaDeAnos))
        spinnerMain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val valor = spinnerMain.selectedItem.toString()

                setupPieChart(valor)

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPieChart(string: String) {


        launch {
            context.let {

                var barEntries = ArrayList<BarEntry>()
                val valor = ProductDatabase(it!!).getProductDao().getAllProducts()
                val listaMeses = DateUtils.getExpenses(valor, string)

                for ((index, value) in listaMeses.withIndex()) {

                    barEntries.add(
                        BarEntry(
                            listaMeses[index].value.toFloat(),
                            DateUtils.getMonthInteger(listaMeses[index].expenses)
                        )
                    )

                }

                var dataset = BarDataSet(barEntries, "Despesas")
                dataset.setColors(ColorTemplate.COLORFUL_COLORS)
                dataset.valueTextSize = 12f
                val labels = listaMeses.map { Product -> Product.expenses }

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

        when (item.itemId) {
            R.id.men_ajuda -> findNavController().navigate(R.id.actionMainToAjuda)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun listaDeAnos() {

        var lista: List<Product> = ArrayList<Product>()


        launch {
            context.let {
                lista = ProductDatabase(it!!).getProductDao().getAllProducts()
                var listaRecebida: List<String> = ArrayList<String>()

                if (lista.isNullOrEmpty()) {
                    listaRecebida = DateUtilsJava.getInstance().getYearNow()
                    setSpinner(listaRecebida)
                } else {
                    listaRecebida = DateUtilsJava.getInstance().getYear(lista)
                    listaRecebida.sort()
                    setSpinner(listaRecebida)
                }
            }
        }
    }
}
