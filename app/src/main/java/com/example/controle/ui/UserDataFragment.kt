package com.example.controle.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.model.Person
import kotlinx.android.synthetic.main.fragment_data_user.*

/**
 * A simple [Fragment] subclass.
 */
class UserDataFragment : BaseFragment() {

    private var person:Person? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setTitle("Dados do usuário")

        return inflater.inflate(R.layout.fragment_data_user, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            person = UserDataFragmentArgs.fromBundle(it).person

            txt_dados_nome.setText(person?.nome)
            txt_dados_cpf.setText(person?.cpf)
            txt_dados_data.setText(person?.dataNascimento)
            txt_dados_telefone.setText(person?.telefone)
            txt_dados_email.setText(person?.email)

        }


        btn_fab_home_dados.setOnClickListener {
            val action = UserDataFragmentDirections.actionsDadosUsuariosToMain()
            Navigation.findNavController(it).navigate(action)
        }

        btn_fab_lista_dados.setOnClickListener {
            val action = UserDataFragmentDirections.actionDadosUsuariosToListaUsuarios()
            Navigation.findNavController(it).navigate(action)
        }

        btn_copy_dados_nome.setOnClickListener {
            if(!txt_dados_nome.text.isEmpty()){

                val clipboardManager = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", txt_dados_nome.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context,"Copiado:\n"+txt_dados_nome.text,Toast.LENGTH_SHORT).show()


                Effects.clickEffect(btn_copy_dados_nome,activity!!)

            }
        }

        btn_copy_dados_cpf.setOnClickListener {
            if(!txt_dados_cpf.text.isEmpty()){

                val clipboardManager = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", txt_dados_cpf.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context,"Copiado:\n"+txt_dados_cpf.text,Toast.LENGTH_SHORT).show()

                Effects.clickEffect(btn_copy_dados_cpf,activity!!)

            }
        }

        btn_copy_dados_data.setOnClickListener {
            if(!txt_dados_data.text.isEmpty()){

                val clipboardManager = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", txt_dados_data.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context,"Copiado:\n"+txt_dados_data.text,Toast.LENGTH_SHORT).show()

                Effects.clickEffect(btn_copy_dados_data,activity!!)

            }
        }

        btn_copy_dados_telefone.setOnClickListener {
            if(!txt_dados_telefone.text.isEmpty()){

                val clipboardManager = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", txt_dados_telefone.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context,"Copiado:\n"+txt_dados_telefone.text,Toast.LENGTH_SHORT).show()

                Effects.clickEffect(btn_copy_dados_telefone,activity!!)

            }
        }

        btn_copy_dados_email.setOnClickListener {
            if(!txt_dados_email.text.isEmpty()){

                val clipboardManager = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", txt_dados_email.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context,"Copiado:\n"+txt_dados_email.text,Toast.LENGTH_SHORT).show()

                Effects.clickEffect(btn_copy_dados_email,activity!!)

            }
        }







    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        AjudaFragment.setNumber(5)

        when(item.itemId){
            R.id.men_ajuda -> findNavController().navigate(R.id.actionDadosUsuarioToAjuda)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }



}
