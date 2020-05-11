package com.example.controle.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.controle.R
import com.example.controle.adapter.PersonAdapter
import com.example.controle.dao.ProductDatabase
import kotlinx.android.synthetic.main.fragment_lista_usuario.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class ListaUsuarioFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setTitle("Usuários cadastrados")
        return inflater.inflate(R.layout.fragment_lista_usuario, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewLista.setHasFixedSize(true)
        recyclerViewLista.layoutManager = StaggeredGridLayoutManager(1,
        StaggeredGridLayoutManager.VERTICAL)
        recyclerViewLista.addItemDecoration(DividerItemDecoration(context,LinearLayout.VERTICAL))

        launch {
            context?.let {
                val per = ProductDatabase(it).getPersonDao().getAllPersons()
                recyclerViewLista.adapter = PersonAdapter(per)
            }
        }


        btn_home_lista_usuarios.setOnClickListener {
            val action = ListaUsuarioFragmentDirections.actionListaUsuariosToMain()
            Navigation.findNavController(it).navigate(action)
        }

        btn_add_lista_usuarios.setOnClickListener {
            val action = ListaUsuarioFragmentDirections.actionListaPersonToAddPerson()
            Navigation.findNavController(it).navigate(action)
        }

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        AjudaFragment.setNumber(1)

        when(item.itemId){
            R.id.men_ajuda -> findNavController().navigate(R.id.actionListaUsuariosToAjuda)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }


}
