package com.example.controle.util;

import com.example.controle.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateUtilsJava {

    private List<Product> listaPrimaria = new ArrayList<>();
    private Set<String> listaUnica = new HashSet<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static DateUtilsJava data;

    public static DateUtilsJava getInstance() {
        if (data == null) {
            data = new DateUtilsJava();
        }
        return data;
    }

    public DateUtilsJava() {

    }


    public List<String> getYear(List<Product> lista) {

        List<String> anos = new ArrayList<>();
        this.listaPrimaria = lista;

        for (Product item : listaPrimaria) {
            listaUnica.add(getDateYear(item.getData()));
        }

        for (String item : listaUnica) {
            anos.add(item);
        }

        return anos;
    }


    private String getDateYear(String data) {
        int numberData = 0;

        try {
            Date dataRecebida = sdf.parse(data);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataRecebida);
            numberData = cal.get(Calendar.YEAR);
        } catch (ParseException ex) {

        }

        return Integer.toString(numberData);
    }

     public ArrayList<String> getYearNow() {

        ArrayList<String> lista = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        lista.add(Integer.toString(cal.get(Calendar.YEAR)));

        return lista;
    }

    private List<String>anoAtual(){
        List<String>ano = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        ano.add(Integer.toString( cal.get(Calendar.YEAR)));
        return  ano;
    }


    public List<String>getElements(){

        List<String> lista = new ArrayList<>();
        lista.add("Agua");
        lista.add("Energia");


        return lista;

    }


}
