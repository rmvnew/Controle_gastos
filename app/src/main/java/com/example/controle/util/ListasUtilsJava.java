package com.example.controle.util;

import com.example.controle.model.ListExpenses;
import com.example.controle.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListasUtilsJava {

    private List<String> listaNova = new ArrayList<>();
    private Set<String> listaunica = new HashSet<>();
    private List<ListExpenses> listaRecebida = new ArrayList<>();

    public ListasUtilsJava() {

    }

    private static ListasUtilsJava listas;

    public static ListasUtilsJava getInstance() {
        if (listas == null) {
            listas = new ListasUtilsJava();
        }
        return listas;
    }


    public List<String> listaDespesasOrdenadas(List<ListExpenses> lista) {

        this.listaRecebida = lista;

        listaunica.add("Agua");
        listaunica.add("Energia");
        listaunica.add("Internet");


        for (ListExpenses item : listaRecebida) {

                listaunica.add(item.getNome());

        }

        for (String item : listaunica) {
            listaNova.add(item);
        }

        Collections.sort(listaNova);

        return listaNova;

    }

    public void limparLista(){
        listaNova.clear();
    }

    private int getYear(String dataRecebida){
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = sdf.parse(dataRecebida);

            cal.setTime(data);

        }catch(Exception ex){

        }

        return cal.get(Calendar.YEAR);
    }

    private int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }


}
