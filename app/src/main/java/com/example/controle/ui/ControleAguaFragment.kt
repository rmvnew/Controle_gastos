package com.example.controle.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.controle.R
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.Product
import com.example.controle.util.AjudaFragment
import com.example.controle.util.BaseFragment
import com.example.controle.util.DateUtils
import com.example.controle.util.DateUtilsJava
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_controle_agua.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class ControleAguaFragment : BaseFragment() {

    var listaNova: List<String> = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.setTitle("Controle de consumo")
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_controle_agua, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        btn_home.setOnClickListener {
            val action = ControleAguaFragmentDirections.actionAguaToHome()
            Navigation.findNavController(it).navigate(action)
        }


        listaDeAnos()


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun listaDeAnos() {


        var lista: List<Product> = ArrayList<Product>()
        var listaEscolhida: List<Product> = ArrayList<Product>()



        launch {
            context.let {
                lista = ProductDatabase(it!!).getProductDao().getAllProducts()

                if (lista.isNullOrEmpty()) {
                    listaNova = DateUtilsJava.getInstance().getYearNow()
                    spinnerSelect(spinnerWhater, listaNova, 1)
                    spinnerSelect(spinnerType, DateUtilsJava.getInstance().elements, 0)
                } else {
                    listaNova = DateUtilsJava.getInstance().getYear(lista)
                    (listaNova as MutableList<String>?)?.sort()
                    spinnerSelect(spinnerWhater, listaNova, 1)
                    spinnerSelect(spinnerType, DateUtilsJava.getInstance().elements, 0)
                }

            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun spinnerSelect(spinner: Spinner, lista: List<String>, type: Int) {
        spinner.adapter =
            context?.let {
                ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, lista)
            }
        if (type == 1) {
            spinner.setSelection(DateUtils.getSpinnerCurrentYear(lista))
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val valor = spinnerWhater.selectedItem.toString()
                val type = spinnerType.selectedItem.toString()

                setupPieChart(valor, type)


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPieChart(ano: String, type: String) {


        launch {
            context.let {

                var lineEntries = ArrayList<Entry>()
                val valor = ProductDatabase(it!!).getProductDao().getAllProducts()
                val listaMeses = DateUtils.getConsumer(valor, type, ano)

                for ((index, value) in listaMeses.withIndex()) {

                    lineEntries.add(
                        Entry(
                            listaMeses[index].value.toFloat(),
                            DateUtils.getMonthInteger(listaMeses[index].monthConsumer)
                        )
                    )

                }

                var dataset = LineDataSet(lineEntries, "Despesas")
                dataset.setColors(ColorTemplate.COLORFUL_COLORS)
                dataset.valueTextSize = 10f
                val labels = listaMeses.map { Product -> Product.monthConsumer }

                val data = LineData(labels, dataset)
                lineChart.setDescription("Despesas")
                lineChart.data = data
                lineChart.invalidate()
                lineChart.animateX(2000)

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        AjudaFragment.setNumber(4)

        when (item.itemId) {
            R.id.men_ajuda -> findNavController().navigate(R.id.actionControleConsumoToAjuda)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


}

