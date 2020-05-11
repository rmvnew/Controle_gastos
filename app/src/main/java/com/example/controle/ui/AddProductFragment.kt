package com.example.controle.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.Product
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddProductFragment : BaseFragment() {


    private var product:Product? = null
    val cal = Calendar.getInstance()
    val ano = cal.get(Calendar.YEAR)
    val mes = cal.get(Calendar.MONTH)
    val dia = cal.get(Calendar.DAY_OF_MONTH)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentContext = inflater.inflate(R.layout.fragment_add_product, container, false)

        (activity as MainActivity).supportActionBar?.setTitle("Cadastro de despesas")

        setHasOptionsMenu(true)

        return currentContext
    }

    fun pick(context: Context){
        val datePickerDialog = DatePickerDialog(context,R.style.DialogTheme,DatePickerDialog.OnDateSetListener { it, ano, mes, dia ->
            edit_text_data.setText("$dia/${mes.toInt()+1}/$ano")
        },ano,mes,dia)
        datePickerDialog.show()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        edit_text_consumo.isEnabled = false
       // setMask(edit_text_data,"data")

        edit_text_data.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                pick(activity!!)
            }
        }

        edit_text_data.setOnClickListener {
            pick(activity!!)
        }

        1


        arguments?.let {
            product = AddProductFragmentArgs.fromBundle(it).product


            edit_text_name.setText(product?.nome)
            edit_text_data.setText(product?.data)
            edit_text_consumo.setText(product?.consumer)
            edit_text_valor.setText(product?.valor)

        }


        btn_home_cad.setOnClickListener {
            Effects.clickEffect(btn_home_cad,activity!!)
            val action = AddProductFragmentDirections.addToMain()
            Navigation.findNavController(it).navigate(action)
        }


        btn_save.setOnClickListener { view ->

            Effects.clickEffect(btn_save,activity!!)

            val prodName = edit_text_name.text.toString().trim()
            val prodData = edit_text_data.text.toString().trim()
            var prodConsumer = edit_text_consumo.text.toString().trim()
            val prodValor = edit_text_valor.text.toString().trim()

            if(prodName.isEmpty()){
                edit_text_name.error = "Informe o nome do produto!!"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            if(prodConsumer.isEmpty()){
                prodConsumer = "0"
            }

            launch {

                context?.let {

                    val prod = Product(prodName,prodData,prodConsumer,prodValor)

                    if(product == null) {

                        ProductDatabase(it).getProductDao().addProduct(prod)
                        it.toast("Produto salvo")
                    }else{
                        prod.id = product!!.id
                        ProductDatabase(it).getProductDao().updateProduct(prod)
                        it.toast("Produto atualizado")
                    }


                    val action = AddProductFragmentDirections.actionSaveProduct()
                    Navigation.findNavController(view).navigate(action)


                }
            }

        }


        btn_remover.setOnClickListener {
            if(product != null){
                deleteProd()
            }else{
                Toast.makeText(context,"Não pode ser deletado",Toast.LENGTH_SHORT).show()
            }
        }



        val options = arrayOf("","Agua","Energia","Internet","Apartamento")


        sp_option.isEnabled = false

        addSwitch_option.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                addSwitch_option.text = "Escolher"
                edit_text_name.isEnabled = false
                sp_option.isEnabled = true

                sp_option.adapter =
                    context?.let {
                        ArrayAdapter<String>(it,android.R.layout.simple_list_item_1,options)
                    }


                sp_option.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        edit_text_name.setText(options.get(position))
                        if(!sp_option.selectedItem.toString().equals("Agua")&&!sp_option.selectedItem.toString().equals("Energia")){
                            edit_text_consumo.isEnabled = false
                        }else{
                            edit_text_consumo.isEnabled = true
                        }

                    }
                }

            }else{
                addSwitch_option.text = "Digitar"
                edit_text_name.isEnabled = true
                edit_text_name.requestFocus()
                sp_option.isEnabled = false
            }
        }



    }

//    fun setMask(editText: EditText,type: String){
//        var typeText = ""
//        when(type){
//            "cpf" -> typeText = "NNN.NNN.NNN-NN"
//            "data" -> typeText = "NN/NN/NNNN"
//            "telefone" -> typeText = "(NN) NNNNN-NNNN"
//        }
//
//        val smf = SimpleMaskFormatter(typeText)
//        val mtw = MaskTextWatcher(editText,smf)
//        editText.addTextChangedListener(mtw)
//
//
//    }


    private fun deleteProd(){

        AlertDialog.Builder(context).apply {
            setTitle("Deletar este produto?")
            setMessage("Esta operação irá apagar este registro.")
            setPositiveButton("Sim"){_,_ ->
                launch {
                    ProductDatabase(context).getProductDao().deleteProduct(product!!)
                    val action = AddProductFragmentDirections.actionSaveProduct()
                    Navigation.findNavController(view!!).navigate(action)

                }
            }

            setNegativeButton("Não"){_,_ ->

            }
        }.create().show()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.men_ajuda -> Toast.makeText(context,"Abre menu ajuda",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

}

