package com.example.controle.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.controle.R
import com.example.controle.adapter.FixedExpensesAdapter
import com.example.controle.dao.ProductDatabase
import kotlinx.android.synthetic.main.fragment_list_of_fixed_expenses.*
import kotlinx.android.synthetic.main.fragment_list_user.*
import kotlinx.coroutines.launch

class ListOfFixedExpensesFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setTitle("Despesas fixas cadastradas")

        return inflater.inflate(R.layout.fragment_list_of_fixed_expenses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView_fixed_expenses.setHasFixedSize(true)
        recyclerView_fixed_expenses.layoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        recyclerView_fixed_expenses.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        launch {
            context?.let {
                val fix = ProductDatabase(it).getFixedExpenses().getAllFixedExpenses()
                recyclerView_fixed_expenses.adapter = FixedExpensesAdapter(fix)

            }
        }

        btn_home_list_fixed_expenses.setOnClickListener {
            val action = ListOfFixedExpensesFragmentDirections.actionListOfFixedExpensesFragmentToMainFragment()
            Navigation.findNavController(it).navigate(action)
        }

        btn_add_fixed_expenses.setOnClickListener {
            val action = ListOfFixedExpensesFragmentDirections.actionListaExpensesToAdd()
            Navigation.findNavController(it).navigate(action)
        }
    }

}