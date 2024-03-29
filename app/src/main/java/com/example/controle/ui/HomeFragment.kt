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
import com.example.controle.adapter.ProductsAdapter
import com.example.controle.dao.ProductDatabase
import com.example.controle.util.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).supportActionBar?.setTitle("Despesas Registradas")

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_product.setHasFixedSize(true)
        recycler_view_product.layoutManager = StaggeredGridLayoutManager(1,
        StaggeredGridLayoutManager.VERTICAL)
        recycler_view_product.addItemDecoration(DividerItemDecoration(context,LinearLayout.VERTICAL))


        launch {
            context?.let {
                val prod = ProductDatabase(it).getProductDao().getAllProducts()
                recycler_view_product.adapter = ProductsAdapter(prod)
            }
        }


        button_add.setOnClickListener {
            val action = HomeFragmentDirections.actionAddProduct()
            Navigation.findNavController(it).navigate(action)
        }

        button_home.setOnClickListener {
            val action = HomeFragmentDirections.listaToMain()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
