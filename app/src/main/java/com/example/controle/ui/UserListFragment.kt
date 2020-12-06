package com.example.controle.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.controle.R
import com.example.controle.adapter.PersonAdapter
import com.example.controle.dao.ProductDatabase
import com.example.controle.util.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_user.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class UserListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setTitle("Usuários cadastrados")
        return inflater.inflate(R.layout.fragment_list_user, container, false)
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
            val action = UserListFragmentDirections.actionListaUsuariosToMain()
            Navigation.findNavController(it).navigate(action)
        }

        btn_add_lista_usuarios.setOnClickListener {
            val action = UserListFragmentDirections.actionListaPersonToAddPerson()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
