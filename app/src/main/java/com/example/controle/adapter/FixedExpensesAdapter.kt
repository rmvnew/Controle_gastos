package com.example.controle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.controle.R
import com.example.controle.model.FixedExpenses
import com.example.controle.ui.UserListFragmentDirections
import kotlinx.android.synthetic.main.fixed_expenses_layout.view.*

class FixedExpensesAdapter(private val fixed:List<FixedExpenses>) : RecyclerView.Adapter<FixedExpensesAdapter.FixedExpensesViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixedExpensesViewHolder {

        return FixedExpensesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fixed_expenses_layout,parent,false)
        )

    }

    override fun getItemCount() = fixed.size

    override fun onBindViewHolder(holder: FixedExpensesViewHolder, position: Int) {



        holder.view.txt_nome_onus.text = fixed[position].expenseName
        holder.view.txt_registro_onus.text = fixed[position].expenseRegistration
        holder.view.txt_valor_onus.text = fixed[position].expenseValue
        holder.view.txt_url_onus.text = fixed[position].expenseUrl



        holder.view.setOnClickListener {view ->

            AlertDialog.Builder(view.context).apply {
                setTitle(fixed[position].expenseName+" Selecionado!!")
                setMessage("Usar esses dados ou editar?")
                setPositiveButton("Usar"){_,_ ->



                }

                setNegativeButton("Editar"){_,_ ->




                }
            }.create().show()

        }
    }

    class  FixedExpensesViewHolder(val view: View):RecyclerView.ViewHolder(view)

}