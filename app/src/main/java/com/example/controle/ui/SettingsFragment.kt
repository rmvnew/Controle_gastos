package com.example.controle.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.controle.R
import kotlinx.android.synthetic.main.fragment_settings.*


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).supportActionBar?.setTitle("Configurações")
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val share = activity!!.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)

        setItemsInvisibility()

        generalStatus(share)

        getStatus()

    }

    private fun generalStatus(share: SharedPreferences) {

        sw_security.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_senha.visibility = View.VISIBLE

                cb_senha.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked){
                        TextInputEditText_password.visibility = View.VISIBLE
                        btn_salvar_password.visibility = View.VISIBLE
                        cb_biometria.visibility = View.VISIBLE
                        txt_password.setText("")
                        txt_password.requestFocus()
                    }else{
                        TextInputEditText_password.visibility = View.INVISIBLE
                        cb_biometria.visibility = View.INVISIBLE
                        cb_biometria.isChecked = false
                        btn_salvar_password.visibility = View.INVISIBLE
                    }
                }
                btn_salvar_password.setOnClickListener {

                    setStatusUserConfig(share)

                }
            } else {
                cb_senha.isChecked = false
                cb_biometria.isChecked = false
                setItemsInvisibility()
                setStatusDefault(share)
            }
        }

    }

    private fun setStatusDefault(share: SharedPreferences) {
        val editor = share.edit()

        editor.putBoolean("SEGURANÇA", false)
        editor.putBoolean("CB_SENHA", false)
        editor.putBoolean("CB_BIOMETRIA", false)
        editor.putString("NOVA_SENHA", "txt_password.text.toString().trim()")
        editor.apply()
    }

    private fun setStatusUserConfig(share: SharedPreferences) {
        val editor = share.edit()
        if(cb_senha.isChecked){
            if(!txt_password.text.toString().trim().equals("")){
                if(!cb_biometria.isChecked){
                    Toast.makeText(context,"Segurança habilitada!!\nAgora você pode habilitar a biometria para facilitar a entrada no app!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Segurança habilitada!!",Toast.LENGTH_LONG).show()
                }
                editor.putBoolean("SEGURANÇA", sw_security.isChecked)
                editor.putBoolean("CB_SENHA", cb_senha.isChecked)
                editor.putBoolean("CB_BIOMETRIA", cb_biometria.isChecked)
                editor.putString("NOVA_SENHA", txt_password.text.toString().trim())
                editor.apply()
            }else{
                setStatusDefault(share)
                Toast.makeText(context,"Modo default carregado.\nConfigure uma senha para habilitar a segurança!",Toast.LENGTH_LONG).show()
                setItemsInvisibility()
                cb_senha.isChecked = false
                sw_security.isChecked = false
            }
        }

    }

    private fun getStatus() {

        val share = activity!!.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        val seg = share.getBoolean("SEGURANÇA", false)
        val sen = share.getBoolean("CB_SENHA", false)
        val bio = share.getBoolean("CB_BIOMETRIA", false)
        val ns = share.getString("NOVA_SENHA", "")

        if (seg == true) {
            sw_security.isChecked = true
        } else {
            sw_security.isChecked = false
        }

        if (sen == true) {
            cb_senha.isChecked = true
        } else {
            cb_senha.isChecked = false
        }

        if (bio == true) {
            cb_biometria.isChecked = true
        } else {
            cb_biometria.isChecked = false
        }

        if (ns.equals("")) {
            txt_password.setText("")
        } else {
            txt_password.setText(ns.toString())
        }


    }

    private fun setItemsInvisibility() {
        cb_senha.visibility = View.INVISIBLE
        cb_biometria.visibility = View.INVISIBLE
        TextInputEditText_password.visibility = View.INVISIBLE
        btn_salvar_password.visibility = View.INVISIBLE
    }
}
