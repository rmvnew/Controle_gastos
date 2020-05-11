package com.example.controle.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.Person
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.fragment_add_person.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddPersonFragment : BaseFragment() {

    private var person:Person? = null
    val cal = Calendar.getInstance()
    val ano = cal.get(Calendar.YEAR)
    val mes = cal.get(Calendar.MONTH)
    val dia = cal.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setTitle("Cadastro de usuário")
        return inflater.inflate(R.layout.fragment_add_person, container, false)
    }

    fun pick(context: Context){
        val datePickerDialog = DatePickerDialog(context,R.style.DialogTheme,DatePickerDialog.OnDateSetListener { it, ano, mes, dia ->
            txtNascimento.setText("$dia/${mes.toInt()+1}/$ano")
        },ano,mes,dia)
        datePickerDialog.show()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            person = AddPersonFragmentArgs.fromBundle(it).person

            txtNome.setText(person?.nome)
            txtCpf.setText(person?.cpf)
            txtNascimento.setText(person?.dataNascimento)
            txtTelefone.setText(person?.telefone)
            txtEmail.setText(person?.email)
            txtAgua.setText(person?.agua)
            txtEnergia.setText(person?.energia)
            txtApartamento.setText(person?.apartamento)

        }


        txtNascimento.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                pick(activity!!)
            }
        }

        txtNascimento.setOnClickListener {
            pick(activity!!)
        }


        btn_home_usuario.setOnClickListener {
            val action = AddPersonFragmentDirections.actionAddPersonToMain()
            Navigation.findNavController(it).navigate(action)
        }

        btn_lista_usuario.setOnClickListener {
            val action = AddPersonFragmentDirections.actionAddPersonToLista()
            Navigation.findNavController(it).navigate(action)
        }

        setMask(txtCpf,"cpf")
       // setMask(txtNascimento,"data")
        setMask(txtTelefone,"telefone")


        btn_save_usuario.setOnClickListener {
            Effects.clickEffect(btn_save_usuario,context!!)

            val personNome = txtNome.text.toString().trim()
            val personCpf = txtCpf.text.toString().trim()
            val personData = txtNascimento.text.toString().trim()
            val personTelefone = txtTelefone.text.toString().trim()
            val personEmail = txtEmail.text.toString().trim()
            val personAgua = txtAgua.text.toString().trim()
            val personEnergia = txtEnergia.text.toString().trim()
            val personApartamento = txtApartamento.text.toString().trim()


            if(personNome.isEmpty()){
                txtNome.error = "Informe um nome"
                txtNome.requestFocus()
            }

            launch {
                context?.let {

                    val per = Person(
                        personNome,
                        personCpf,
                        personData,
                        personTelefone,
                        personEmail,
                        personAgua,
                        personEnergia,
                        personApartamento
                    )

                    if(person == null){
                        ProductDatabase(it).getPersonDao().addPerson(per)
                        it.toast("Usuario salvo")
                    }else{
                        per.id = person!!.id
                        ProductDatabase(it).getPersonDao().updatePerson(per)
                        it.toast("Usuário atualizado")
                    }


                }

            }


        }


        btn_remover.setOnClickListener {
            if(person != null){
                deletePer()
            }else{
                Toast.makeText(context,"Não pode ser deletado",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun setMask(editText: EditText,type: String){
        var typeText = ""
        when(type){
            "cpf" -> typeText = "NNN.NNN.NNN-NN"
            "data" -> typeText = "NN/NN/NNNN"
            "telefone" -> typeText = "(NN) NNNNN-NNNN"
        }

       val smf = SimpleMaskFormatter(typeText)
       val mtw = MaskTextWatcher(editText,smf)
       editText.addTextChangedListener(mtw)


    }


    private fun deletePer(){
        AlertDialog.Builder(context).apply {
            setTitle("Deletar esse usuário?")
            setMessage("Esta operação irá apagar este registro.")
            setPositiveButton("Sim"){_,_->
                launch {
                    ProductDatabase(context).getPersonDao().deletePerson(person!!)
                    val action = AddPersonFragmentDirections.actionAddPersonToLista()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("Não"){_,_->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        AjudaFragment.setNumber(2)

        when(item.itemId){
            R.id.men_ajuda -> findNavController().navigate(R.id.actionAddPersonToAjuda)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }




}
