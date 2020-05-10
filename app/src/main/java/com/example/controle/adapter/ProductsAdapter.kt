package com.example.controle.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.controle.R
import com.example.controle.model.Product
import com.example.controle.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.product_layout.view.*

class ProductsAdapter(private val prods:List<Product>): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_layout,parent,false)
        )

    }

    override fun getItemCount() = prods.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       holder.view.txt_nome.text = prods[position].nome
       holder.view.txt_data.text = prods[position].data
       holder.view.txt_consumo.text = prods[position].consumer
       holder.view.txt_valor.text = prods[position].valor


       holder.view.setOnClickListener {view ->

           AlertDialog.Builder(view.context).apply {
               setTitle(prods[position].nome+" Selecionado!!")
               setMessage("Editar esse lançamento?")
               setPositiveButton("Sim"){_,_ ->

                   val action = HomeFragmentDirections.actionAddProduct()
                   action.product = prods[position]
                   Navigation.findNavController(view).navigate(action)

               }

               setNegativeButton("Não"){_,_ ->



               }
           }.create().show()

       }
    }

    class  ProductViewHolder(val view: View):RecyclerView.ViewHolder(view)

}