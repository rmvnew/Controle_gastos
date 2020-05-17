package com.example.controle.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.Navigation

import com.example.controle.R
import com.example.controle.animation.Effects
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_security.*

/**
 * A simple [Fragment] subclass.
 */
class SecurityFragment : BaseFragment() {

    val handler = Handler()


    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var biometricManager: BiometricManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).supportActionBar?.setTitle("Autenticação")
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

        return inflater.inflate(R.layout.fragment_security, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val share = activity!!.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        val seg = share.getBoolean("SEGURANÇA", false)
        val sen = share.getBoolean("CB_SENHA", false)
        val bio = share.getBoolean("CB_BIOMETRIA", false)
        val ns = share.getString("NOVA_SENHA", "")

        if (seg == true) {
            loadDefault()
        } else {
            goToHomeActivity()

        }

    }

    private fun loadDefault() {

        val share = activity!!.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        val seg = share.getBoolean("SEGURANÇA", false)
        val sen = share.getBoolean("CB_SENHA", false)
        val bio = share.getBoolean("CB_BIOMETRIA", false)
        val ns = share.getString("NOVA_SENHA", "")

        Effects.rotateEffect(circulo, context!!)

        if (bio == true) {
            biometricSettings()
        } else {
            finger.visibility = View.INVISIBLE
        }

        btn_entrar.setOnClickListener {
            Effects.clickEffect(btn_entrar, context!!)

            if(txt_password.text.toString().equals(ns.toString())) {
                goToHomeActivity()
            }else{
                showToast("Senha inválida!")
            }

            
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun checkBiometricStatus(biometricManager: BiometricManager) {

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.e(
                    "MY_APP_TAG", "The user hasn't associated " +
                            "any biometric credentials with their account."
                )
        }


    }

    private fun biometricSettings() {
        biometricManager = BiometricManager.from(context!!)
        val executor = ContextCompat.getMainExecutor(context!!)

        checkBiometricStatus(biometricManager)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Erro na operação $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    goToHomeActivity()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Falha na autenticação")

                }

            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Controle de Acesso")
            .setDescription("Use o leitor biometrico para efetuar login")
            .setNegativeButtonText("Ou use o pin para logar")
            .build()

        finger.setOnClickListener {

            Effects.clickEffect(finger, context!!)
            biometricPrompt.authenticate(promptInfo)
            showToast("Encoste o dedo no leitor biometrico")
        }
    }

    private fun goToHomeActivity() {

        val action = SecurityFragmentDirections.actionSecurityToMain()
        Navigation.findNavController(view!!).navigate(action)


    }

}
