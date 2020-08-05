package com.example.controle.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Html
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.ListExpenses
import com.example.controle.model.Product
import com.example.controle.util.ListUtilsJava
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AddProductFragment : BaseFragment() {


    private var product: Product? = null
    private val cal = Calendar.getInstance()
    private val ano = cal.get(Calendar.YEAR)
    private val mes = cal.get(Calendar.MONTH)
    private val dia = cal.get(Calendar.DAY_OF_MONTH)
    private var lista: List<String> = ArrayList<String>()
    private var SALVAR_ITEM_LISTA = true
    private val REQUEST_CODE_SPEECH_INPUT = 100


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        (activity as MainActivity).supportActionBar?.setTitle("Cadastro de despesas")

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    fun pick(context: Context) {
        val datePickerDialog = DatePickerDialog(
            context,
            R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { it, ano, mes, dia ->
                edit_text_data.setText("$dia/${mes.toInt() + 1}/$ano")
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val share = activity!!.getSharedPreferences("ITEM_SAVE", Context.MODE_PRIVATE)
        getStatus(share)
        edit_text_consumo.isEnabled = false


        setMask(edit_text_data, "data")

        arguments?.let {
            product = AddProductFragmentArgs.fromBundle(it).product


            edit_text_name.setText(product?.nome)
            edit_text_data.setText(product?.data)
            edit_text_consumo.setText(product?.consumer)
            edit_text_valor.setText(product?.valor)

        }


        btn_home_cad.setOnClickListener {
            Effects.clickEffect(btn_home_cad, activity!!)
            val action = AddProductFragmentDirections.addToMain()
            Navigation.findNavController(it).navigate(action)
        }


        btn_salvar.setOnClickListener { view ->

            Effects.clickEffect(btn_salvar, activity!!)
            val parametroConfirmado = share.getBoolean("SALVAR_PREFERENCIA",false)

            val prodName = edit_text_name.text.toString().trim()
            val prodData = edit_text_data.text.toString().trim()
            var prodConsumer = edit_text_consumo.text.toString().trim()
            val prodValor = edit_text_valor.text.toString().trim()

            if (prodName.isEmpty()) {
                edit_text_name.error = "Informe o nome do produto!!"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            if (prodConsumer.isEmpty()) {
                prodConsumer = "0"
            }

            launch {

                context?.let {

                    val prod = Product(prodName, prodData, prodConsumer, prodValor)
                    val param = ListExpenses(prodName)

                    if (product == null) {

                        ProductDatabase(it).getProductDao().addProduct(prod)
                        if(parametroConfirmado){
                            //caso esteja habilitado salva o cadastro de despesa na lista de preferencia
                            ProductDatabase(it).getListExpensesDao().addList(param)
                            Toast.makeText(context,Html.fromHtml("<font color=#FFD700>Information</font><br>" +
                                    "O item:${prodName.toString()}, foi adicionado a sua lista de preferencias"),Toast.LENGTH_LONG).show()
                        }
                        it.toast("Produto salvo")

                    } else {

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
            if (product != null) {
                deleteProd()
            } else {
                Toast.makeText(context, "Não pode ser deletado", Toast.LENGTH_SHORT).show()
            }
        }

        launch {
            context?.let {

                val listaBanco = ProductDatabase(it).getListExpensesDao().getAllList()
                lista = ListUtilsJava.getInstance().listaDespesasOrdenadas(listaBanco)

            }
        }

        ListUtilsJava.getInstance().limparLista()


        sp_option.isEnabled = false
        sp_option.visibility = View.INVISIBLE

        addSwitch_option.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addSwitch_option.text = "Escolher"
                edit_text_name.isEnabled = false
                sp_option.visibility = View.VISIBLE
                sw_item_lista.visibility = View.INVISIBLE
                setSpinner()
                SALVAR_ITEM_LISTA = false
                btn_mic.visibility = View.INVISIBLE

            } else {
                addSwitch_option.text = "Digitar"
                edit_text_name.isEnabled = true
                edit_text_name.requestFocus()
                edit_text_name.setText("")
                edit_text_consumo.isEnabled = false
                sp_option.isEnabled = false
                sp_option.visibility = View.INVISIBLE
                sw_item_lista.visibility = View.VISIBLE
                SALVAR_ITEM_LISTA = true //Sera?
                btn_mic.visibility = View.VISIBLE

            }
        }


        sw_item_lista.setOnCheckedChangeListener { _, isChecked ->
            val editor = share.edit()
            if (isChecked && SALVAR_ITEM_LISTA) {
                editor.putBoolean("SALVAR_PREFERENCIA", true)
                editor.apply()
                Toast.makeText(
                    context, Html.fromHtml(
                        "<h3><font color=#4682B4><b>Preferência modificada:</b></font></h3><br>" +
                                "<h6>Agora os items que você cadastrar serão " +
                                "salvos na lista.</h6>"
                    ), Toast.LENGTH_LONG
                ).show()
            } else {
                editor.putBoolean("SALVAR_PREFERENCIA", false)
                editor.apply()

                Toast.makeText(
                    context, Html.fromHtml(
                        "<h3><font color=#4682B4><b><font size=6>Preferência modificada:</b></font></h3><br>" +
                                "<h6>Agora os items que você cadastrar <font color=#FF0000><b>não</b></font> serão " +
                                "salvos na lista.</h6>"
                    ), Toast.LENGTH_LONG
                ).show()
            }
        }


        btn_mic.setOnClickListener {
            speak()
        }


    }

    private fun speak(){
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Olá fale alguma coisa!")

        try {
            startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)
        }catch(ex: Exception){
            Toast.makeText(requireContext(),ex.message,Toast.LENGTH_SHORT).show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){

            REQUEST_CODE_SPEECH_INPUT ->{
                if(resultCode == Activity.RESULT_OK && null != data){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    edit_text_name.setText(result[0])
                    edit_text_data.requestFocus()
                }
            }
        }

    }

    private fun setSpinner() {
        sp_option.isEnabled = true




        sp_option.adapter =
            context?.let {
                ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, lista)
            }



        sp_option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                edit_text_name.setText(lista.get(position))
                edit_text_consumo.isEnabled = !(sp_option.selectedItem.toString() != "Agua" && sp_option.selectedItem.toString() != "Energia")

            }
        }
    }

    fun setMask(editText: EditText, type: String) {
        var typeText = ""
        when (type) {
            "cpf" -> typeText = "NNN.NNN.NNN-NN"
            "data" -> typeText = "NN/NN/NNNN"
            "telefone" -> typeText = "(NN) NNNNN-NNNN"
        }

        val smf = SimpleMaskFormatter(typeText)
        val mtw = MaskTextWatcher(editText, smf)
        editText.addTextChangedListener(mtw)


    }

    private fun deleteProd() {

        AlertDialog.Builder(context).apply {
            setTitle("Deletar este produto?")
            setMessage("Esta operação irá apagar este registro.")
            setPositiveButton("Sim") { _, _ ->
                launch {
                    ProductDatabase(context).getProductDao().deleteProduct(product!!)
                    val action = AddProductFragmentDirections.actionSaveProduct()
                    Navigation.findNavController(view!!).navigate(action)

                }
            }

            setNegativeButton("Não") { _, _ ->

            }
        }.create().show()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        AjudaFragment.setNumber(3)

        when (item.itemId) {
            R.id.men_ajuda -> findNavController().navigate(R.id.actionAddDispesasToAjuda)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    fun getStatus(share: SharedPreferences) {
        val option = share.getBoolean("SALVAR_PREFERENCIA", false)
        sw_item_lista.isChecked = option == true
    }

}

