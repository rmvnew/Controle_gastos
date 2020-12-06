package com.example.controle.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.controle.R
import com.example.controle.animation.Effects
import com.example.controle.dao.ProductDatabase
import com.example.controle.model.FixedExpenses
import com.example.controle.util.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_fixed_expenses.*
import kotlinx.coroutines.launch


class AddFixedExpensesFragment : BaseFragment() {

    private var fixedExpenses:FixedExpenses? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setTitle("Cadastro de ônus Fixo")

        return inflater.inflate(R.layout.fragment_add_fixed_expenses, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_home_usuario.setOnClickListener {
            it.findNavController().navigate(R.id.action_addFixedExpensesFragment_to_mainFragment)
        }

        btn_save_fixed_expenses.setOnClickListener {view ->
            Effects.clickEffect(btn_save_fixed_expenses,context!!)

            val fixedExpensesName = nomeFixedExpenses.text.toString().trim()
            val fixedExpensesRegister = registroFixedExpenses.text.toString().trim()
            val fixedExpensesValue = valueFixedExpenses.text.toString().trim()
            val fixedExpensesUrl = urlFixedExpenses.text.toString().trim()

            if(fixedExpensesName.isEmpty()){
                nomeFixedExpenses.error = "Informe um nome"
                nomeFixedExpenses.requestFocus()
            }else if(fixedExpensesValue.isEmpty()){
                valueFixedExpenses.error = "Informe um valor"
            }else{

                launch {
                    context?.let {
                        val fix = FixedExpenses(
                            fixedExpensesName,
                            fixedExpensesRegister,
                            fixedExpensesValue,
                            fixedExpensesUrl
                        )

                        if(fixedExpenses == null){
                            ProductDatabase(it).getFixedExpenses().addFixedExpenses(fix)
                            it.toast("Registro salvo com Sucesso!!")



                        }else{
                            fix.id = fixedExpenses!!.id
                            ProductDatabase(it).getFixedExpenses().updateFixedExpenses(fix)
                            it.toast("Registro atualizado")
                            //lançar para lista
                        }

                        val action = AddFixedExpensesFragmentDirections.actionAddToListExpenses()
                        Navigation.findNavController(view).navigate(action)

                    }
                }



            }



        }

        btn_remover.setOnClickListener {
            if(fixedExpenses != null){
                deleteReg()
            }
        }

    }

    private fun deleteReg(){
        AlertDialog.Builder(context).apply {

            setTitle("Deletar esse Registro?")
            setMessage("Esta operação irá apagar este registro.")
            setPositiveButton("Sim"){_,_->
                launch {
                    ProductDatabase(context).getFixedExpenses().deleteFixedExpenses(fixedExpenses!!)

                    //lançar para lista

                }
            }
            setNegativeButton("Não"){_,_->

            }

        }.create().show()
    }


}