package com.example.controle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.controle.R
import com.example.controle.model.Person
import com.example.controle.ui.UserListFragmentDirections

import kotlinx.android.synthetic.main.person_layout.view.*

class PersonAdapter(private val pers:List<Person>) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {

        return PersonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.person_layout,parent,false)
        )

    }

    override fun getItemCount() = pers.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        holder.view.txtViewNome.text = pers[position].nome
        holder.view.txtViewCpf.text = pers[position].cpf
        holder.view.txtViewData.text = pers[position].dataNascimento
        holder.view.txtViewTelefone.text = pers[position].telefone
        holder.view.txtViewEmail.text = pers[position].email




        holder.view.setOnClickListener {view ->

            AlertDialog.Builder(view.context).apply {
                setTitle(pers[position].nome+" Selecionado!!")
                setMessage("Usar esses dados ou editar?")
                setPositiveButton("Usar"){_,_ ->



                }

                setNegativeButton("Editar"){_,_ ->


                   val action = UserListFragmentDirections.actionListaPersonToAddPerson()
                   action.person = pers[position]
                   Navigation.findNavController(view).navigate(action)

                }
            }.create().show()

        }
    }

    class  PersonViewHolder(val view: View):RecyclerView.ViewHolder(view)

}